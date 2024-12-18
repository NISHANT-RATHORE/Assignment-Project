# Assignment Project

## 📋 Project Overview

This project is a backend application built using Spring Boot. It provides APIs for managing students and subjects, implements JWT-based security, and incorporates role-based authorization.

---

## ⚙️ Features

1. **Entities**:
   - **Student**:
     - Fields: `id`, `name`, `email`, `password`, `address`, `roles`, `subjects`.
     - Relationship: A student can enroll in multiple subjects.
   - **Subject**:
     - Fields: `id`, `name`, `student`.
     - Relationship: A subject is associated with one student.
2. **REST APIs**:
   - Endpoints are secured with JWT-based authentication and role-based authorization.
3. **Security**:
   - JWT-based authentication and authorization.
   - User roles: `STUDENT` and `ADMIN`.
4. **Database**:
   - H2 in-memory database for storing entities.
5. **Default Admin**:
   - A default admin user is created when the application starts.

---

## 🔐 Role-Based Authorization

Role-based access control is implemented to restrict access to APIs based on user roles.

### Access Rules

| Endpoint                          | Allowed Roles          | Description                                           |
|-----------------------------------|------------------------|-------------------------------------------------------|
| `/students/create`                | Public                 | Open to all users for registering a new student.      |
| `/students/getAll`                | `ADMIN`                | Accessible only to admin users.                      |
| `/h2-console/**`                  | Public                 | Open for database console access during development.  |
| `/students/login`                 | `STUDENT`              | Accessible to students for logging in.               |
| `/students/registerSubject`       | `STUDENT`              | Allows students to register for a subject.           |
| `/students/**`                    | `STUDENT`, `ADMIN`     | Accessible to both students and admin users.         |
| `/subjects/create`                | `ADMIN`                | Allows admin to create new subjects.                 |
| `/subjects/getAll`                | `STUDENT`, `ADMIN`     | Accessible to both students and admin users.         |

---

## 📜 API Endpoints

### Student Endpoints

| Method | Endpoint                  | Description                          |
|--------|---------------------------|--------------------------------------|
| POST   | `/students/create`        | Register a new student.              |
| POST   | `/students/login`         | Login a student and generate a JWT.  |
| GET    | `/students/getAll`        | Get a list of all registered students. |
| POST   | `/students/registerSubject` | Register a subject for the logged-in student. |

### Subject Endpoints

| Method | Endpoint                  | Description                          |
|--------|---------------------------|--------------------------------------|
| POST   | `/subjects/create`        | Create a new subject.                |
| GET    | `/subjects/getAll`        | Get a list of all registered subjects. |

---

## 🧑‍💼 Default Admin User

When the application starts, a default admin user is initialized in the database with the following credentials:

- **Email**: `admin@example.com`
- **Password**: `adminpassword`
- **Role**: `ADMIN`

The admin user can be used to test admin-specific functionality or as a manager for the system.

---

## 🚀 How to Set Up and Run the Application

### Prerequisites

1. Install JDK (version 19 or higher).
2. Install Maven (if not included in your IDE).
3. Ensure `JAVA_HOME` is correctly configured.

### Steps to Run Locally

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/NISHANT-RATHORE/Assignment-Project.git
   cd Assignment
   ```

2. **Build the Project**:
   Run the following command to build the project:
   ```bash
   mvn clean install
   ```

3. **Run the Application**:
   Start the application using:
   ```bash
   mvn spring-boot:run
   ```

4. **Access H2 Console**:
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:dcbapp`
   - Username: `sa`
   - Password: `password`

5. **Login as Admin**:
   Use the default admin credentials to log in:
   - Email: `admin@example.com`
   - Password: `adminpassword`

6. **API Testing**:
   Use a tool like Postman to test the APIs. The base URL for APIs is `http://localhost:8080`.
   Here's the shortened version of your **API Endpoints** section:

- **Create a Student**: `POST /students/create`  (Admin only)
- **Login a Student**: `POST /students/login`  
- **Get All Students**: `GET /students/getAll` (Admin only)  
- **Create a Subject**: `POST /subjects/create` (Admin only)  
- **Get All Subjects**: `GET /subjects/getAll`  
- **Register to a Subject**: `POST /students/registerSubject` (Student with valid JWT)
