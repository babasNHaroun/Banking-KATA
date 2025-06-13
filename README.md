# SG-Kata

This repository contains two main projects:

- **Banking-KATA**: Java Spring Boot backend for banking operations.
- **banking-ui**: Angular frontend with Angular Material for interacting with the backend.

---

## Project Structure

```
SG-Kata/
├── Banking-KATA/      # Spring Boot backend
└── banking-ui/        # Angular frontend
```

---

## 1. Banking-KATA (Spring Boot Backend)

### Prerequisites

- Java 19 (or as specified in `pom.xml`)
- Maven

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

### Prerequisites

- Node.js (v16+ recommended)
- Angular CLI (`npm install -g @angular/cli`)

### Running the Frontend

```sh
cd banking-ui
npm install
ng serve
```

The frontend will start on [http://localhost:4200](http://localhost:4200).

---

## Features

- Create Account
- Deposit & Withdraw
- View Transactions
- View Account Statement
- Angular Material UI with sidebar navigation

---

## Usage

1. **Start the backend** (`Banking-KATA`).
2. **Start the frontend** (`banking-ui`).
3. Open [http://localhost:4200](http://localhost:4200) in your browser.

---

