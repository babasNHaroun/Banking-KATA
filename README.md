# SG-Kata

This repository contains two main projects:

- **Banking-KATA**: Java Spring Boot backend for banking operations.
- **banking-ui**: Angular frontend with Angular Material for interacting with the backend.

---

## Project Structure

```
SG-Kata/
├── Banking-KATA/                # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/banking/
│   │   │   │   ├── api/          # REST controllers
│   │   │   │   ├── model/        # Domain models (e.g., Account, Transaction)
│   │   │   │   ├── service/      # Business logic/services
│   │   │   │   └── BankingKataApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   │       └── java/com/banking/ # Unit and integration tests
│   └── pom.xml                   # Maven build file
│
└── banking-ui/                   # Angular frontend
    ├── src/
    │   ├── app/
    │   │   ├── components/         # Feature components (deposit, withdraw, transactions, statement)
    │   │   ├── services/         # Angular services (e.g., account.service.ts)
    │   │   ├── models/           # TypeScript interfaces/models
    │   │   ├── app.component.ts  # Main app component
    │   │   └── app.routes.ts     # Routing configuration
    │   └── assets/
    │
    ├── angular.json              # Angular CLI config
    ├── package.json              # NPM dependencies and scripts
    └── README.md                 # Frontend documentation
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

    Of course, you can create as many accounts as you want with the Rest API /acounts POST endpoint using a Rest client if you want to.
  )
- Deposit & Withdraw
- View Transactions
- View Account Statement
- Sidebar navigation

---

## 1. Banking-KATA (Spring Boot Backend)

### Running the Backend

```sh
cd Banking-KATA
mvn spring-boot:run
```

The backend will start on [http://localhost:8080](http://localhost:8080).

### CORS

CORS is enabled for `http://localhost:4200` to allow the Angular frontend to communicate with the backend.

---

## 2. banking-ui (Angular Frontend)

### Running the Frontend

```sh
cd banking-ui
npm install
ng serve
```

The frontend will start on [http://localhost:4200](http://localhost:4200).

---

