# 💰 FinanceApp – Personal Finance Management API

FinanceApp is a robust backend REST API designed for personal finance management. It allows users to track income and expenses, manage categories, set savings goals, and generate detailed financial reports.

The project is built using **Java 21** and **Spring Boot**, adhering to a clean layered architecture with session-based authentication and real-world business validations.

---

## ✨ Features

### 👤 User Authentication & Security
* **Secure Registration:** User registration with strict input validations.
* **Session Management:** Login & logout using session-based authentication.
* **Data Isolation:** Complete separation of data between users.
* **Security:** Centralized exception handling and protection for secured endpoints.

### 🗂 Category Management
* **System & Custom Categories:** Pre-seeded default categories (Income & Expense) and support for user-defined custom categories.
* **Validation:** Prevents duplicate category names per user.
* **Integrity:** Default categories and categories currently in use by transactions cannot be deleted.

### 💸 Transaction Management
* **CRUD Operations:** Create, read, update (immutable dates), and delete income/expense transactions.
* **Robust Validations:**
  * Amounts must be positive.
  * Future-dated transactions are restricted.
  * Category validation.
* **Filtering:** Filter transactions by category or specific date ranges.

### 🎯 Savings Goals
* **Goal Tracking:** Set target amounts and dates.
* **Smart Calculation:** Automatic progress tracking based on:
  $$\text{Progress} = \text{Total Income} - \text{Total Expenses} (\text{since goal start})$$
* **Real-time Updates:** Progress, remaining amount, and percentage update automatically when transactions change.

### 📊 Financial Reports
* **Insights:** Generate monthly and yearly financial summaries.
* **Breakdowns:** Category-wise analysis of income and expenses.
* **Net Savings:** Automated calculation of net savings.

---

## 🏗 Architecture

The application follows a strict **Layered Architecture** to ensure separation of concerns:

`Controller` → `Service` → `Repository`

* **Controller Layer:** Handles REST API endpoints and request mapping.
* **Service Layer:** Contains core business logic and validations.
* **Repository Layer:** Manages data persistence using **Spring Data JPA**.
* **DTOs:** Separates internal entities from request/response models.
* **Global Exception Handler:** Centralized error management for consistent API responses.

---

## 🛠 Technology Stack

### Backend
* **Language:** Java 21
* **Framework:** Spring Boot 4.0.1 (Web, Security, Data JPA)
* **Build Tool:** Maven

### Database
* **Development:** H2 (In-memory database)
* **Production Capable:** PostgreSQL (Ready for integration)

### Tools & DevOps
* **Containerization:** Docker
* **Utilities:** Lombok

---

## 🔐 Security 

### Security Implementation
* **Session-Based Auth:** Stateless REST principles with session management.
* **Access Control:** Unauthorized and Forbidden access handling.
* **Data Privacy:** No sensitive data is exposed in API responses.



---

## 🌐 API Endpoints

### Authentication
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new user |
| `POST` | `/api/auth/login` | Login user |
| `POST` | `/api/auth/logout` | Logout user |

### Categories
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/categories` | List all categories |
| `POST` | `/api/categories` | Create a custom category |
| `DELETE` | `/api/categories/{name}` | Delete a category |

### Transactions
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/transactions` | Get all transactions (with filters) |
| `POST` | `/api/transactions` | Create a transaction |
| `PUT` | `/api/transactions/{id}` | Update a transaction |
| `DELETE` | `/api/transactions/{id}` | Delete a transaction |

### Savings Goals
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/goals` | List all savings goals |
| `GET` | `/api/goals/{id}` | Get specific goal details |
| `POST` | `/api/goals` | Create a savings goal |
| `PUT` | `/api/goals/{id}` | Update a goal |
| `DELETE` | `/api/goals/{id}` | Delete a goal |

### Reports
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/reports/monthly/{year}/{month}` | Get monthly financial report |
| `GET` | `/api/reports/yearly/{year}` | Get yearly financial summary |

### System
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/health` | Health check |

---

## 🗄 Database Schema

The system uses a relational model with the following entities:

1.  **Users:** Stores credentials and profile info.
2.  **Category:** Types of income/expenses (linked to Users).
3.  **Transaction:** Financial records linked to Categories and Users.
4.  **Savings Goal:** Targets tracked against User's financial progress.

> **Note:** Default categories (Salary, Food, Rent, Utilities) are seeded at startup via `DataInitializer`.

---

## ⚙ Configuration

**`application.properties`**
```properties
spring.application.name=FinanceApp

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true

```
## Data Seeding

The application is seeded with some default data for categories upon startup. This includes categories like "Salary", "Food", "Rent", etc. This is handled by the `DataInitializer` class.


## ▶ Getting Started

### Prerequisites
* Java 21
* Maven
* Docker (Optional)

### Run Locally
```bash
# Clone the repository
git clone [https://github.com/yourusername/financeapp.git](https://github.com/yourusername/financeapp.git)

# Navigate to directory
cd financeapp

# Run using Maven wrapper
./mvnw spring-boot:run

```

*Application starts at: `http://localhost:8080*`

### 🐳 Docker Support

**Build Image**

```bash
docker build -t financeapp .
```

**Run Container**

```bash
docker run -p 8080:8080 financeapp
```
