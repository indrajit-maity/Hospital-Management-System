<div align="center">

# 🏥 Hospital Management System

### Enterprise-Grade Hospital Management Backend Using by Spring Boot

*A production-ready, secure, and scalable RESTful backend for managing hospital operations — patients, doctors, appointments, and medical records — built with modern Java engineering practices.*

</div>

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)
![Build Status](https://img.shields.io/badge/Build-Passing-success?style=for-the-badge&logo=githubactions&logoColor=white)

</div>

<p align="center">
  <a href="#-overview">Overview</a> •
  <a href="#-key-features">Features</a> •
  <a href="#-architecture">Architecture</a> •
  <a href="#-installation">Installation</a> •
  <a href="#-api-endpoints">API</a> •
  <a href="#-security">Security</a> •
  <a href="#-contribution-guide">Contributing</a>
</p>

---

## 📑 Table of Contents

- [Overview](#-overview)
- [Key Features](#-key-features)
    - [Authentication & Security](#-authentication--security)
    - [Authorization](#-authorization)
    - [Hospital Features](#-hospital-features)
    - [Database](#-database)
    - [Docker](#-docker)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Application Configuration](#-application-configuration)
- [API Endpoints](#-api-endpoints)
- [Docker Commands](#-docker-commands)
- [Database Migration](#-database-migration)
- [Security](#-security)
- [Error Handling](#-error-handling)
- [Author](#-author)

---

## 🩺 Overview

The **Hospital Management System** is a production-ready backend application built with **Spring Boot**, designed to simplify and streamline healthcare management through secure and scalable RESTful APIs.

The system provides comprehensive modules for managing **patients**, **doctors**, **appointments**, and **medical records**, enabling efficient hospital operations with a clean and well-structured architecture.

To ensure enterprise-grade security, the application implements **Spring Security**, **JWT-based authentication**,**OAuth2 Authentication using Google and GitHub**, **Role-Based Access Control (RBAC)**, and **permission-based authorization**, providing fine-grained access control for different user roles.

---

## ✨ Key Features
## 🔐 Authentication & Authorization Flow

```mermaid
flowchart LR

A[Client] -->|Login Request| B[Authentication]
B --> C{Valid Credentials?}

C -- No --> D[401 Unauthorized]

C -- Yes --> E[Generate JWT & Refresh Token]
E --> F[Return JWT to Client]

F --> G[Client Sends JWT]
G --> H[JWT Authentication Filter]

H --> I{Valid Token?}

I -- No --> J[401 Unauthorized]

I -- Yes --> K[Load User Details]
K --> L[Check Roles & Permissions]

L --> M{Authorized?}

M -- No --> N[403 Forbidden]

M -- Yes --> O[Controller]
O --> P[Service]
P --> Q[Repository]
Q --> R[(MySQL Database)]
R --> S[Return Response]
```
### 🏥 Hospital Features

- **Patient Management** — Full CRUD operations for patient profiles, demographics, and medical history.
- **Doctor Management** — Onboarding, specialization tracking, and availability management for doctors.
- **Appointment Management** — Scheduling, rescheduling, cancellation, and status tracking of appointments.
- **Medical Record Management** — Secure creation and retrieval of diagnoses, prescriptions, and treatment history.
- **User Management** — Centralized management of all system users and their credentials.
- **Role Management** — Creation and configuration of roles within the system.
- **Permission Management** — Granular control over what each role or user is allowed to do.

## 🗄️ Database Schema Design

The Hospital Management System follows a **relational database design** to maintain data integrity and efficiently manage relationships between different entities. The schema is normalized to reduce redundancy while ensuring scalability and maintainability.

```mermaid
erDiagram

    PATIENT {
        bigint id PK
        string name
        string gender
        date birthDate
        string email
        string bloodGroup
        datetime createdAt
        bigint insurance_id FK
    }

    DOCTOR {
        bigint id PK
        string name
        string specialization
        string email
        datetime createdAt
    }

    APPOINTMENT {
        bigint id PK
        datetime appointmentTime
        string reason
        string status
        bigint doctor_id FK
        bigint patient_id FK
    }

    INSURANCE {
        bigint id PK
        string policyNumber
        string provider
        date validUntil
        datetime createdAt
    }

    DEPARTMENT {
        bigint id PK
        string name
        datetime createdAt
        bigint head_doctor_id FK
    }

    DOCTOR_DEPARTMENT {
        bigint doctor_id PK,FK
        bigint department_id PK,FK
    }

    PATIENT ||--o{ APPOINTMENT : books
    DOCTOR ||--o{ APPOINTMENT : handles

    INSURANCE ||--o{ PATIENT : has

    DOCTOR }o--o{ DEPARTMENT : assigned_to

    DOCTOR ||--o| DEPARTMENT : head_of

    DOCTOR ||--o{ DOCTOR_DEPARTMENT : belongs
    DEPARTMENT ||--o{ DOCTOR_DEPARTMENT : contains
```

## 🏗️ Architecture

The application follows a classic **layered (N-tier) architecture**, ensuring clear separation of concerns, testability, and maintainability.

### 🏗️ Application Layer Flow

```mermaid
flowchart TD

    A[🌐 Client]
    -->|📨 HTTPS Request| B[🚀 Embedded Tomcat]

    B --> C[🔐 Spring Security]

    C --> D[🛡️ JWT Authentication Filter]

    D --> E{✅ Token Valid?}

    E -- ❌ No --> F[🚫 401 Unauthorized]

    E -- ✅ Yes --> G[🎮 Controller]

    G --> H[⚙️ Service Layer]

    H --> I[🗄️ Repository Layer]

    I --> J[(💾 MySQL Database)]

    J --> I

    I --> H

    H --> G

    G --> K[📦 HTTP Response]

    G -. ⚠️ Exception .-> L[🛑 Global Exception Handler]

    L --> M[❗ Error Response]

    M --> A

    K --> A
```

Each incoming request passes through the `JwtAuthenticationFilter`, which validates the token signature and expiration, loads the user's authorities, and populates the `SecurityContext` — enabling downstream `@PreAuthorize` checks at the controller and service layers.

---

## 🌳 Project Structure
## 📂 Project Structure

```text
Hospital-Management-System
│
├── 📦 src
│   ├── 📂 main
│   │   ├── 📂 java
│   │   │   └── 📂 com.HospitalManagement.ManagedHospital
│   │   │
│   │   │   ├── 📂 configuration
│   │   │   │   ├── OpenApiConfig.java
│   │   │   │   ├── MapperConfiguration.java
│   │   │   │   └── ...
│   │   │   │
│   │   │   ├── 📂 controller
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── DoctorController.java
│   │   │   │   ├── PatientController.java
│   │   │   │   ├── HospitalController.java
│   │   │   │   └── ...
│   │   │   │
│   │   │   ├── 📂 dto
│   │   │   │   ├── request/
│   │   │   │   ├── response/
│   │   │   │   ├── auth/
│   │   │   │   └── ... (All Request & Response DTOs)
│   │   │   │
│   │   │   ├── 📂 entity
│   │   │   │   ├── User.java
│   │   │   │   ├── Patient.java
│   │   │   │   ├── Doctor.java
│   │   │   │   ├── Department.java
│   │   │   │   ├── Appointment.java
│   │   │   │   ├── Insurance.java
│   │   │   │   ├── Admin.java
│   │   │   │   ├── DoctorDepartment.java
│   │   │   │   └── ...
│   │   │   │
│   │   │   ├── 📂 repository
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── PatientRepository.java
│   │   │   │   ├── DoctorRepository.java
│   │   │   │   ├── AppointmentRepository.java
│   │   │   │   ├── DepartmentRepository.java
│   │   │   │   ├── InsuranceRepository.java
│   │   │   │   └── ...
│   │   │   │
│   │   │   ├── 📂 service
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── PatientService.java
│   │   │   │   ├── DoctorService.java
│   │   │   │   ├── AppointmentService.java
│   │   │   │   ├── DepartmentService.java
│   │   │   │   ├── InsuranceService.java
│   │   │   │   └── ...
│   │   │   │
│   │   │   ├── 📂 security
│   │   │   │   ├── JwtAuthFilter.java
│   │   │   │   ├── WebSecurityConfig.java
│   │   │   │   ├── OAuth2SuccessHandler.java
│   │   │   │   ├── CustomUserDetailService.java
│   │   │   │   ├── RolePermissionMapping.java
│   │   │   │   ├── AuthUtil.java
│   │   │   │   └── ...
│   │   │   │
│   │   │   ├── 📂 error
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── ApiError.java
│   │   │   │   └── ...
│   │   │   │
│   │   │   ├── 📂 type
│   │   │   │   ├── RoleType.java
│   │   │   │   ├── PermissionType.java
│   │   │   │   ├── AuthProviderType.java
│   │   │   │   ├── BloodGroupType.java
│   │   │   │   └── ...
│   │   │   │
│   │   │   └── 🚀 ManagedHospitalApplication.java
│   │   │
│   │   └── 📂 resources
│   │       ├── 📂 db
│   │       │   └── 📂 migration
│   │       │       ├── V1__initial_schema.sql
│   │       │       └── ...
│   │       ├── 📂 static
│   │       ├── 📂 templates
│   │       ├── application.yml
│   │       ├── application.properties
│   │       ├── application-prod.properties
│   │       └── data.sql
│   │
│   └── 📂 test
│       ├── 📂 service
│       ├── 📂 controller
│       ├── 📂 repository
│       └── ...
│
├── 🐳 Dockerfile
├── 🐳 docker-compose.yml
├── 📦 pom.xml
├── 📄 README.md
└── 📄 .env
```

---

## ✅ Prerequisites

Ensure the following tools are installed before setting up the project:

| Tool | Minimum Version |
|---|-----------------|
| Java (JDK) | 21+             |
| Maven | 3.9+            |
| Docker | 24.x+           |
| Docker Compose | 2.x+            |
| MySQL (if run locally without Docker) | latest          |

---

## 🚀 Installation

### 1️⃣ Clone Repository

```bash
git clone https://github.com/<your-username>/hospital-management-system.git
cd hospital-management-system
```

### 2️⃣ Maven Build

```bash
mvn clean install -DskipTests
```

### 3️⃣ Docker Build

```bash
docker build -t hms-backend:latest -f docker/Dockerfile .
```

### 4️⃣ Docker Network Creation

```bash
docker network create hms-network
```

### 5️⃣ Run MySQL Container

```bash
docker run -d \
  --name hms-mysql \
  --network hms-network \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=hospital_db \
  -p 3307:3306 \
  mysql:latest
```

### 6️⃣ Run Spring Boot Container

```bash
docker run -d \
  --name hms-backend \
  --network hms-network \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://hms-mysql:3306/hospital_db \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=root \
  -p 8080:8080 \
  hms-backend:latest
```
### 🐳 Run with Docker Compose

Start both the **Spring Boot application** and **MySQL database** in detached mode:

```bash
docker compose up -d
```

Or, if you're using a dedicated `docker/` directory:

```bash
cd docker
docker compose up -d
```

To stop the containers:

```bash
docker compose down
```

To rebuild the images after making changes:

```bash
docker compose up --build -d
```

To view running containers:

```bash
docker compose ps
```

To view application logs:

```bash
docker compose logs -f
```

---



## 📡 API Endpoints

> Base URL: `http://localhost:8080/indra`

### 🔐 Authentication

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/register` | Register a new user |
| `POST` | `/auth/login` | Authenticate and receive JWT + refresh token |

### 🧑‍🤝‍🧑 Patients

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/patient/newPatient` | Create a new patient record |
| `POST` | `/patient/newAppointment` | Book a new appointment for a patient |
| `POST` | `/patients/all` | Retrieve all patient records |
| `GET` | `/patients/{id}` | Retrieve a patient by ID |
### 👨‍⚕️ Doctors

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/doctor/doctors` | Retrieve all registered doctors |
| `GET` | `/doctor/appointments` | Retrieve appointments assigned to the logged-in doctor |
### 👨‍💼 Admin

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/admin/onBoardnewDoctor` | Add a new doctor to the hospital system |
| `GET` | `/admin/patients` | Retrieve a list of all registered patients |
| `GET` | `/admin/home` | Retrieve the admin dashboard and overview |

---
## 📖 Swagger API Documentation

The project includes interactive API documentation powered by **Swagger UI (OpenAPI 3)**.

The screenshots below provide a complete overview of all available REST endpoints.

### 📄 Swagger API - Oauth2 Authorization

<p align="center">
  <a href="src/main/resources/assets/page-1.png.png">
    <img src="src/main/resources/assets/page-1.png" width="900" alt="Swagger Page 1">
  </a>
</p>

---

### 📄 Swagger API - Page 2

<p align="center">
  <a href="src/main/resources/assets/page-2.png">
    <img src="src/main/resources/assets/Page-2.png" width="900" alt="Swagger Page 2">
  </a>
</p>

---

### 📄 Swagger API - Page 3

<p align="center">
  <a href="src/main/resources/assets/page-3.png.png">
    <img src="src/main/resources/assets/page-3.png" width="900" alt="Swagger Page 3">
  </a>
</p>

---

### 📄 Swagger API - Page 4

<p align="center">
  <a href="src/main/resources/assets/page-4.png.png">
    <img src="src/main/resources/assets/page-4.png" width="900" alt="Swagger Page 3">
  </a>
</p>

> 💡 **Tip:** Click on any screenshot to view it in full resolution.




### Role Hierarchy

```
                ┌─────────┐
                │  ADMIN   │  (Full system access)
                └────┬────┘
                     │
                ┌────▼────┐
                │  DOCTOR  │  (Clinical operations)
                └────┬────┘
                     │
                ┌────▼────┐
                │ PATIENT  │  (Self-service access)
                └─────────┘
```
## 🔐 Role-Based Access Control (RBAC)

| Role | Permissions |
|------|-------------|
| **ADMIN** | `PATIENT_READ`, `PATIENT_WRITE`, `APPOINTMENT_READ`, `APPOINTMENT_WRITE`, `APPOINTMENT_DELETE`, `USER_MANAGE`, `REPORT_VIEW` |
| **DOCTOR** | `PATIENT_READ`, `PATIENT_WRITE`, `APPOINTMENT_READ`, `APPOINTMENT_WRITE`, `APPOINTMENT_DELETE` |
| **PATIENT** | `PATIENT_READ`, `APPOINTMENT_READ`, `APPOINTMENT_WRITE` |
---

## ⚠️ Error Handling

The application implements a centralized `@ControllerAdvice`-based exception handler that returns consistent, structured error responses:

```json
{
  "timestamp": "2026-01-15T10:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Patient with ID 42 not found",
  "path": "/api/v1/patients/42"
}
```
---

## 🔮 Future Enhancements

- 📧 Email/SMS notifications for appointment reminders
- 🧾 Billing and invoicing module
- 🗓️ Doctor availability calendar with time-slot booking
- 🧪 Lab test result management

---

## 👤 Author

<div align="center">

**Indrajit Maity**

<a href="https://github.com/indrajit-maity">
  <img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" alt="GitHub">
</a>
&nbsp;
<a href="https://www.linkedin.com/in/indrajit-maity-2a7061285/">
  <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn">
</a>
&nbsp;
<a href="mailto:2005indrajitmaity@gmail.com">
  <img src="https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white" alt="Email">
</a>

</div>

---

