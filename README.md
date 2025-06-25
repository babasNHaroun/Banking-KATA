# SG-Kata

This repository contains two main projects:

- **backend**: Java Spring Boot backend for banking operations.
- **frontend**: Angular frontend with Angular Material for interacting with the backend.

---

## Project Structure

```
SG-Kata/
├── backend/                   # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/banking/
│   │   │   │   ├── controller/     # REST controllers
│   │   │   │   ├── domain/         # Domain logic/entities
│   │   │   │   ├── model/          # DTOs or supporting models
│   │   │   │   ├── service/        # Business logic/services
│   │   │   │   ├── exception/      # Exception handling
│   │   │   │   ├── repository/     # Data access layer
│   │   │   │   └── BankingKataApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   │       └── java/com/banking/   # Unit and integration tests
│   └── pom.xml                     # Maven build file
│
└── frontend/                     # Angular frontend
    ├── src/
    │   ├── app/
    │   │   ├── components/         # Angular components (deposit, withdraw, transactions, statement)
    │   │   ├── services/           # Angular services 
    │   │   ├── models/             # TypeScript interfaces/models
    │   │   ├── app.component.ts    # Main app component
    │   │   └── app.routes.ts       # Routing configuration
    │   └── assets/
    │
    ├── angular.json                # Angular CLI config
    ├── package.json                # NPM dependencies and scripts
    └── README.md                   # Frontend documentation
```

---

## Prerequisites

- **Node.js:**  
  You must have **Node.js v19 or higher** installed to run the Angular frontend.

- **Java:**  
  You must have **Java 17 or higher** installed to build and run the Spring Boot backend.

---


## Features

This app uses an inMemory storage DB mechanism for the purpose of simplicity so as to focus on architecture.
Thus, data is erased every time the backend restarts.

  ( 
    for simplicity, a default account with ID 'account-123' is created by clicking the 'Create Account' button in the homepage.

    This small angular app does not support the creation and management of accounts.

    Of course, you can create as many accounts as you want with the Rest API /accounts POST endpoint using a Rest client if you want to.
  )
- Deposit & Withdraw
- View Transactions
- View Account Statement
- Sidebar navigation

---

## 1. Backend

### Running the Backend

```sh
cd backend
mvn spring-boot:run
```

The backend will start on [http://localhost:8080](http://localhost:8080).

### CORS

CORS is enabled for `http://localhost:4200` to allow the Angular frontend to communicate with the backend.

---

## 2. Frontend

### Running the Frontend

```sh
cd frontend
npm install
ng serve -o
```

The frontend will start on [http://localhost:4200](http://localhost:4200).

---

