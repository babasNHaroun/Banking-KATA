# Jenkins CI/CD Setup for SG-Kata Banking Application

## Overview
This guide will help you set up Jenkins locally to automate the execution of unit and integration tests for both the backend (Spring Boot/Maven) and frontend (Angular/npm) applications, including GitHub integration.

## Prerequisites
- ✅ Jenkins installed via Homebrew
- ✅ Java 17+ (OpenJDK@21 installed)
- ✅ Maven 3.9.10+ installed
- ✅ Node.js 18+ installed
- ✅ Git installed
- GitHub account and repository

## Step-by-Step Setup

### 1. Jenkins Initial Configuration

1. **Access Jenkins**: http://localhost:8080
2. **Initial Admin Password**: `e57f66eecec74696be78217fbfef85e7`
3. **Install Suggested Plugins** when prompted
4. **Create Admin User** with your preferred credentials
5. **Set Jenkins URL**: Keep default (http://localhost:8080)

### 2. Essential Plugin Installation

Navigate to **Manage Jenkins > Manage Plugins > Available** and install:

- ✅ Git plugin
- ✅ GitHub plugin  
- ✅ Pipeline plugin
- ✅ NodeJS plugin
- ✅ Maven Integration plugin
- ✅ HTML Publisher plugin
- ✅ Test Results Analyzer plugin
- ✅ Workspace Cleanup plugin
- ✅ Build Timeout plugin

### 3. Global Tool Configuration

Go to **Manage Jenkins > Global Tool Configuration**:

#### JDK Configuration
- **Name**: `JDK-21`
- **JAVA_HOME**: `/opt/homebrew/Cellar/openjdk@21/21.0.7/libexec/openjdk.jdk/Contents/Home`
- **Install automatically**: Unchecked (since we're using local installation)

#### Maven Configuration
- **Name**: `Maven-3.9.10`
- **MAVEN_HOME**: `/opt/homebrew/Cellar/maven/3.9.10/libexec`
- **Install automatically**: Unchecked (since we're using local installation)

#### NodeJS Configuration  
- **Name**: `NodeJS-18`
- **Installation**: Install automatically
- **Version**: NodeJS 18.x (LTS)

#### Git Configuration
- **Name**: `Default`
- **Path to Git executable**: `/opt/homebrew/bin/git`

### 4. Create Pipeline Job

1. **New Item** → Enter job name: `SG-Kata-CI-Pipeline`
2. **Select Item Type**: Choose **Pipeline** from the list of project types:
   - Freestyle project
   - **Pipeline** ← Select this one
   - Multibranch Pipeline
   - Maven project
   - External Job
3. Click **OK**
4. **Pipeline Configuration**:
   - **Definition**: Pipeline script from SCM
   - **SCM**: Git
   - **Repository URL**: Your GitHub repository URL
   - **Credentials**: Add GitHub credentials (username/token)
   - **Branches to build**: `*/main` (or your default branch)
   - **Script Path**: `Jenkinsfile`
   
5. **Build Triggers** (Choose one):
   - **Manual**: No automatic triggers (build manually)
   - **Poll SCM**: Check "Poll SCM" and set schedule (e.g., `H/5 * * * *` for every 5 minutes)
   - **Build periodically**: Set schedule for regular builds

### 5. GitHub Integration Setup

#### A. GitHub Webhook Configuration (NOT SUPPORTED for Local Jenkins)
⚠️ **Important**: GitHub webhooks cannot reach local Jenkins (127.0.0.1:8080) because it's not accessible over the public internet.

**Alternative Solutions**:
1. **Manual Pipeline Execution**: Trigger builds manually from Jenkins UI
2. **Polling SCM**: Configure Jenkins to poll GitHub for changes
3. **ngrok Tunnel** (Advanced): Expose local Jenkins to internet temporarily
4. **Cloud Jenkins**: Use Jenkins in cloud (AWS, Google Cloud, etc.)

#### B. GitHub Credentials in Jenkins (Required for Git operations)
1. **Manage Jenkins** → **Manage Credentials**
2. **Global** → **Add Credentials**
3. **Kind**: Username with password
4. **Username**: Your GitHub username
5. **Password**: GitHub Personal Access Token
6. **ID**: `github-credentials`

### 6. Pipeline Configuration (Jenkinsfile)

The `Jenkinsfile` includes:

```groovy
pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9.10'
        nodejs 'NodeJS-18'
    }
    
    stages {
        stage('Checkout') { ... }
        stage('Build Backend') { ... }
        stage('Backend Unit Tests') { ... }
        stage('Backend Integration Tests') { ... }
        stage('Build Frontend') { ... }
        stage('Frontend Unit Tests') { ... }
        stage('Package Backend') { ... }
    }
    
    post {
        always { cleanWs() }
        success { echo 'Pipeline succeeded!' }
        failure { echo 'Pipeline failed!' }
    }
}
```

### 7. Test Execution Summary

#### Backend Tests (Maven/Spring Boot)
- **Unit Tests**: `mvn test`
- **Integration Tests**: `mvn test -Dtest=**/*IntegrationTest`
- **Test Reports**: `target/surefire-reports/`
- **Current Status**: ✅ 20 tests passing

#### Frontend Tests (Angular/Karma)
- **Unit Tests**: `npm test -- --watch=false --browsers=ChromeHeadless`
- **Test Reports**: `coverage/` directory
- **Requirements**: Chrome browser for headless testing

### 8. Pipeline Triggers (Local Jenkins Limitations)

Since GitHub webhooks don't work with local Jenkins, the pipeline will trigger via:
- ✅ Manual execution from Jenkins UI
- ✅ SCM Polling (if configured - checks GitHub every X minutes)
- ✅ Scheduled builds (if configured)
- ❌ ~~Push to main branch~~ (requires public webhook endpoint)
- ❌ ~~Pull requests to main branch~~ (requires public webhook endpoint)

**Recommended for Local Development**:
1. **Manual Triggers**: Build when needed
2. **SCM Polling**: `H/10 * * * *` (check every 10 minutes)

**For Production Use**: Consider cloud-hosted Jenkins or CI/CD services like GitHub Actions

### 9. Monitoring and Reporting

Jenkins will provide:
- ✅ Test result trends
- ✅ Build history
- ✅ Console output logs
- ✅ Artifact archiving (JAR files)
- ✅ HTML test reports

### 10. Local Development Testing

Before pushing to GitHub, test locally:

```bash
# Backend tests
cd backend
mvn clean test

# Frontend tests (requires Chrome)
cd frontend
npm test -- --watch=false --browsers=ChromeHeadless

# Full build
cd backend && mvn clean package
cd frontend && npm run build
```

## Project Structure

```
SG-Kata/
├── Jenkinsfile                 # Jenkins pipeline definition
├── .github/workflows/ci.yml    # GitHub Actions (alternative)
├── jenkins-setup.sh           # Setup helper script
├── backend/                   # Spring Boot application
│   ├── pom.xml               # Maven configuration
│   ├── src/main/java/        # Application code
│   └── src/test/java/        # Unit & integration tests
└── frontend/                 # Angular application
    ├── package.json          # npm configuration
    ├── src/app/              # Application code
    └── src/app/**/*.spec.ts  # Unit tests
```

## Current Test Status

### Backend Tests ✅
- AccountDomainTest: 9 tests passing
- AccountStatementTest: 2 tests passing  
- AccountConcurrencyTest: 2 tests passing
- InMemoryAccountRepositoryTest: 1 test passing
- AccountControllerIntegrationTest: 6 tests passing
- **Total**: 20/20 tests passing

### Frontend Tests ⚠️
- Requires Chrome browser configuration
- Tests available but need headless Chrome setup

## Next Steps

1. ✅ Complete Jenkins initial setup
2. ✅ Install required plugins
3. ✅ Configure global tools
4. ✅ Create pipeline job
5. ✅ Set up GitHub credentials (webhooks not supported locally)
6. ⏳ Test pipeline execution
7. ⏳ Configure build triggers (manual or polling)
8. ⏳ Configure notifications (email/Slack)

## Troubleshooting

### Common Issues:
1. **Maven not found**: Check Global Tool Configuration paths
2. **Node/npm not found**: Install NodeJS plugin and configure
3. **GitHub webhook fails**: ⚠️ Not supported for local Jenkins (127.0.0.1)
4. **Tests fail**: Review console output and test reports
5. **Git authentication fails**: Check GitHub credentials setup

## Local Development Workarounds

### Option 1: Manual Execution (Recommended for Local)
- Build and test locally before committing
- Trigger Jenkins builds manually when needed
- Use Jenkins for integration testing and reporting

### Option 2: SCM Polling Configuration (Step-by-Step)

**To configure Jenkins to automatically check GitHub for changes:**

1. **Open Your Pipeline Job**:
   - Go to Jenkins dashboard
   - Click on your job name (`SG-Kata-CI-Pipeline`)

2. **Configure Build Triggers**:
   - Click **"Configure"** (left sidebar)
   - Scroll down to **"Build Triggers"** section
   - Check the box ☑️ **"Poll SCM"**

3. **Set Polling Schedule**:
   - In the **"Schedule"** text box, enter a cron expression:
   ```
   H/10 * * * *    # Check every 10 minutes
   H/5 * * * *     # Check every 5 minutes  
   H * * * *       # Check every hour
   ```
   
4. **Cron Expression Format**:
   ```
   MINUTE HOUR DAY MONTH DAYOFWEEK
   ```
   - `H` = Use hash of job name for random distribution
   - `*/10` = Every 10 units
   - `*` = Every unit

5. **Common Polling Schedules**:
   - `H/10 * * * *` - Every 10 minutes (recommended for active development)
   - `H/30 * * * *` - Every 30 minutes (for less active repos)
   - `H 9-17 * * 1-5` - Every hour during business hours (9am-5pm, Mon-Fri)

6. **Save Configuration**:
   - Click **"Save"** at the bottom

**How It Works**:
- Jenkins will check your GitHub repository at the specified intervals
- If new commits are found, it will automatically trigger a build
- No webhooks needed - Jenkins initiates the check

**Example Workflow**:
1. You push code to GitHub at 2:15 PM
2. Jenkins polls at 2:20 PM (next 10-minute interval)
3. Jenkins detects new commits
4. Build starts automatically
5. Tests run and results are available

**Verification**:
- Go to your job's main page
- Look for **"Git Polling Log"** in the left sidebar
- This shows when Jenkins checked for changes and what it found

**Pro Tips**:
- Use `H` instead of `0` to distribute load (Jenkins randomizes the exact minute)
- Don't poll too frequently (every 1-2 minutes) as it can overload GitHub API
- Check the polling log to confirm it's working

### Option 3: Using ngrok for Webhooks (Advanced)
```bash
# Install ngrok
brew install ngrok

# Expose Jenkins to internet temporarily
ngrok http 8080

# Use the ngrok URL for GitHub webhook
# Example: https://abc123.ngrok.io/github-webhook/
```

⚠️ **Security Warning**: Only use ngrok for testing, never in production.

## Additional Features

### Future Enhancements:
- SonarQube integration for code quality
- Docker containerization
- Deployment automation
- Performance testing
- Security scanning

---

**Jenkins URL**: http://localhost:8080  
**Initial Password**: `e57f66eecec74696be78217fbfef85e7`
