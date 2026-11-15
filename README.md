
# Student Management System

A full-stack Student Management System built using **Java, Spring Boot, Thymeleaf, and PostgresSQL**. The application provides a structured platform to manage student records, enroll students in courses, and handle student information through a secure and user-friendly interface.

The project follows a layered architecture to maintain clean code, separation of concerns, and easier maintainability.

## 🚀 Features

- **Student Management (CRUD):** Create, view, update, and delete student records.
- **Course Enrollment:** Manage student enrollment and associated course details.
- **Form Validation:** Implement server-side validation to ensure accurate data entry.
- **Pagination & Sorting:** Efficiently navigate and organize student records.
- **Authentication & Authorization:** Secure application access using Spring Security.
- **Global Exception Handling:** Handle application errors in a centralized manner.
- **DTO & Entity Separation:** Separate API or application data from database entities.
- **Dashboard:** Access student management information through a centralized dashboard.
- **Responsive UI:** User-friendly interface built with Thymeleaf templates.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 17/21 | Programming Language |
| Spring Boot | Backend Framework |
| Spring MVC | Web Application Architecture |
| Spring Data JPA | Database Operations |
| Hibernate | ORM Framework |
| Spring Security | Authentication & Authorization |
| Thymeleaf | Server-Side Template Engine |
| PostgreSQL | Relational Database |
| Maven / Gradle | Dependency Management |

## 📁 Project Architecture

The application follows a layered architecture:

- **Controller Layer:** Handles incoming HTTP requests and manages application navigation.
- **Service Layer:** Contains business logic and application operations.
- **Repository Layer:** Handles database interactions using Spring Data JPA.
- **Entity Layer:** Defines database entities and their relationships.
- **DTO Layer:** Manages data transfer between application layers.
- **Exception Layer:** Handles application exceptions centrally.
- **Configuration Layer:** Contains application and security configurations.
- **View Layer:** Contains Thymeleaf templates for the user interface.

## ⚙️ Getting Started

Follow these steps to run the project locally.

### Prerequisites

Make sure you have installed:

- Java JDK 17 or later
- PostgreSQL Server
- Maven or Gradle
- Git
- IntelliJ IDEA / Eclipse / VS Code

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR-USERNAME/student-management-system.git
```

### 2. Navigate to the Project Directory

```bash
cd student-management-system
```

### 3. Configure the Database

Create a PostgreSQL database:

```sql
CREATE DATABASE student_management;
```

Update your `application.properties` file with your PostgresSql credentials:

```properties
spring.datasource.url=jdbc:jdbc:postgresql://localhost:5432/student_mgmt_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Run the Application

For Maven:

```bash
mvn spring-boot:run
```

Or, if using Gradle:

```bash
./gradlew bootRun
```

### 5. Access the Application

Open your browser and visit:

```text
http://localhost:8080
```

## 🔐 Security

The application uses Spring Security to manage authentication and authorization.

Security configuration controls access to application resources and helps protect restricted functionality.

## 🎯 Project Objectives

- Understand real-world Spring Boot application development.
- Implement layered architecture and separation of concerns.
- Work with relational databases using Spring Data JPA and Hibernate.
- Implement authentication and authorization using Spring Security.
- Build server-rendered web pages using Thymeleaf.
- Practice validation, pagination, sorting, and exception handling.

## 👨‍💻 Author

**Md Arman Ansari**

- GitHub: https://github.com/arman23-dev
- LinkedIn: https://www.linkedin.com/in/armaanansari23

---

⭐ If you find this project useful, consider giving the repository a star!
