#!/bin/bash

echo "🔍 Jenkins CI/CD Setup Validation"
echo "=================================="

# Check Jenkins status
echo ""
echo "📋 Jenkins Service Status:"
if brew services list | grep jenkins | grep -q "started"; then
    echo "✅ Jenkins is running"
    echo "🌐 Access at: http://localhost:8080"
else
    echo "❌ Jenkins is not running"
    echo "💡 Start with: brew services start jenkins"
fi

# Check tools
echo ""
echo "🛠️  Development Tools:"

# Java
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    echo "✅ Java: $JAVA_VERSION"
else
    echo "❌ Java not found"
fi

# Maven
if command -v mvn &> /dev/null; then
    MVN_VERSION=$(mvn -version | head -n 1 | cut -d' ' -f3)
    echo "✅ Maven: $MVN_VERSION"
else
    echo "❌ Maven not found"
fi

# Node.js
if command -v node &> /dev/null; then
    NODE_VERSION=$(node --version)
    echo "✅ Node.js: $NODE_VERSION"
else
    echo "❌ Node.js not found"
fi

# npm
if command -v npm &> /dev/null; then
    NPM_VERSION=$(npm --version)
    echo "✅ npm: $NPM_VERSION"
else
    echo "❌ npm not found"
fi

# Git
if command -v git &> /dev/null; then
    GIT_VERSION=$(git --version | cut -d' ' -f3)
    echo "✅ Git: $GIT_VERSION"
else
    echo "❌ Git not found"
fi

# Test backend build
echo ""
echo "🧪 Backend Tests:"
cd backend
if mvn test -q > /dev/null 2>&1; then
    echo "✅ Backend tests passing"
else
    echo "❌ Backend tests failing"
fi
cd ..

# Check frontend setup
echo ""
echo "🎨 Frontend Setup:"
cd frontend
if [ -d "node_modules" ]; then
    echo "✅ Node modules installed"
else
    echo "⚠️  Node modules not installed (run: npm install)"
fi

if npm run build > /dev/null 2>&1; then
    echo "✅ Frontend builds successfully"
else
    echo "❌ Frontend build failing"
fi
cd ..

# Check project files
echo ""
echo "📁 Project Files:"
if [ -f "Jenkinsfile" ]; then
    echo "✅ Jenkinsfile present"
else
    echo "❌ Jenkinsfile missing"
fi

if [ -f ".github/workflows/ci.yml" ]; then
    echo "✅ GitHub Actions workflow present"
else
    echo "❌ GitHub Actions workflow missing"
fi

echo ""
echo "🎯 Next Steps:"
echo "1. Complete Jenkins setup at http://localhost:8080"
echo "2. Install suggested plugins"
echo "3. Configure GitHub webhook"
echo "4. Create pipeline job using Jenkinsfile"
echo ""
echo "📖 See JENKINS_SETUP_GUIDE.md for detailed instructions"
