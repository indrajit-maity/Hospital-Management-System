<div align="center">

# 🏥 Hospital Management System

### Enterprise-Grade Hospital Management Backend Powered by Spring Boot

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
    - [Roles](#-roles)
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
- [Deployment](#-deployment)
- [Future Enhancements](#-future-enhancements)
- [Contribution Guide](#-contribution-guide)
- [License](#-license)
- [Author](#-author)

---

## 🩺 Overview

The **Hospital Management System (HMS)** is a production-ready backend application designed to digitize and streamline core hospital operations. It provides a secure, role-driven platform for managing **patients, doctors, appointments, medical records, and administrative workflows** through a clean, well-documented RESTful API.

Built with **Spring Boot** and following **layered (N-tier) architecture** principles, the system emphasizes:

- 🔐 **Security-first design** — JWT-based stateless authentication with fine-grained, role-and-permission-based authorization.
- 🧱 **Clean architecture** — Clear separation of concerns across Controller, Service, and Repository layers.
- 🐳 **Portability** — Fully containerized with Docker for consistent deployment across environments.
- 🗃️ **Reliable data management** — MySQL with Flyway-managed, version-controlled schema migrations.

This project exists to serve as both a **real-world reference implementation** of enterprise Spring Boot practices and a **functional foundation** for hospitals or clinics seeking a digital patient-and-appointment management solution.

---

## ✨ Key Features

### 🔑 Authentication & Security

| Feature | Description |
|---|---|
| **JWT Authentication** | Stateless, token-based authentication using signed JSON Web Tokens, eliminating server-side session storage. |
| **Refresh Token Support** | Long-lived refresh tokens allow clients to obtain new access tokens without re-authenticating, improving UX while keeping access tokens short-lived and secure. |
| **Spring Security Integration** | Deep integration with the Spring Security filter chain for request-level authentication and authorization enforcement. |
| **Password Encryption (BCrypt)** | All user passwords are hashed using the BCrypt adaptive hashing algorithm before persistence — plaintext passwords are never stored. |
| **Stateless Authentication** | No server-side session state; each request is authenticated independently via the JWT, enabling horizontal scalability. |

### 🛡️ Authorization

| Feature | Description |
|---|---|
| **Role-Based Authentication** | Users authenticate with credentials tied to a specific role (Admin, Doctor, Patient). |
| **Role-Based Authorization (RBAC)** | API access is restricted based on the authenticated user's assigned role. |
| **Authority-Based Authorization** | Fine-grained `GrantedAuthority` checks complement role checks for granular endpoint protection. |
| **Fine-Grained Permission Management** | Individual permissions (e.g., `PATIENT_READ`, `APPOINTMENT_WRITE`) can be assigned independently of roles. |
| **Dynamic Permission Assignment** | Administrators can assign or revoke permissions to users at runtime without redeploying the application. |
| **Permission Mapping** | Permissions are mapped to roles and/or individual users through a dedicated mapping layer. |
| **User-Specific Permissions** | Supports overriding default role permissions on a per-user basis for exceptional access cases. |
| **Custom Security Configuration** | A tailored `SecurityFilterChain` defines public, authenticated, and role-restricted endpoints explicitly. |
| **Method-Level Security** | `@PreAuthorize` / `@Secured` annotations enforce authorization directly at the service and controller method level. |
| **Secure API Access** | All sensitive endpoints require a valid JWT and appropriate authority, validated on every request. |

### 👥 Roles

The system defines three core roles, each with a distinct set of responsibilities and permissions.

<table>
<tr><th>Role</th><th>Capabilities</th></tr>
<tr>
<td><b>🛠️ Admin</b></td>
<td>

- Manage users (create, update, deactivate)
- Assign roles to users
- Assign and revoke permissions
- Manage doctor records
- Manage patient records
- Manage appointments across the system
- View system-wide analytics and reports

</td>
</tr>
<tr>
<td><b>👨‍⚕️ Doctor</b></td>
<td>

- View assigned patients
- Manage appointment schedules
- Update patient medical records
- Write and manage prescriptions

</td>
</tr>
<tr>
<td><b>🧑‍🤝‍🧑 Patient</b></td>
<td>

- Register and log in to the platform
- Book new appointments
- View appointment history
- Update personal profile information
- View issued prescriptions

</td>
</tr>
</table>

### 🏥 Hospital Features

- **Patient Management** — Full CRUD operations for patient profiles, demographics, and medical history.
- **Doctor Management** — Onboarding, specialization tracking, and availability management for doctors.
- **Appointment Management** — Scheduling, rescheduling, cancellation, and status tracking of appointments.
- **Medical Record Management** — Secure creation and retrieval of diagnoses, prescriptions, and treatment history.
- **User Management** — Centralized management of all system users and their credentials.
- **Role Management** — Creation and configuration of roles within the system.
- **Permission Management** — Granular control over what each role or user is allowed to do.

### 🗄️ Database

- **MySQL Integration** — Relational data persistence via Spring Data JPA and Hibernate.
- **Flyway Migration** — Version-controlled, repeatable schema migrations applied automatically on startup.
- **Database Versioning** — Every schema change is tracked as an immutable, incrementally versioned SQL script.
- **Automatic Schema Migration** — No manual DDL execution required; Flyway applies pending migrations on application boot.

### 🐳 Docker

- **Dockerized Spring Boot Application** — The backend is packaged into a lightweight, portable container image.
- **Dockerized MySQL** — The database runs as an isolated, reproducible container.
- **Docker Network** — A dedicated bridge network enables secure, isolated communication between containers.
- **Container Communication** — The application container resolves the database container by service name over the internal Docker network.

---

## 🏗️ Architecture

The application follows a classic **layered (N-tier) architecture**, ensuring clear separation of concerns, testability, and maintainability.

### Application Layer Flow

```
┌────────────┐
│   Client    │  (Web / Mobile / Postman)
└─────┬──────┘
      │  HTTPS Request
      ▼
┌─────────────────────┐
│    Spring Boot        │
│   (Embedded Tomcat)    │
└─────┬────────────────┘
      ▼
┌─────────────┐
│  Controller  │  → Handles HTTP requests, validates input, returns responses
└─────┬───────┘
      ▼
┌─────────────┐
│   Service    │  → Business logic, transaction management
└─────┬───────┘
      ▼
┌─────────────┐
│ Repository   │  → Data access via Spring Data JPA
└─────┬───────┘
      ▼
┌─────────────┐
│    MySQL     │  → Persistent relational storage
└─────────────┘
```

### 🔐 Security Flow

```
┌────────────┐
│   Client    │
└─────┬──────┘
      │  Request + JWT (Authorization Header)
      ▼
┌─────────────────────┐
│     JWT Filter        │  → Extracts & validates the token
└─────┬────────────────┘
      ▼
┌───────────────────────────┐
│  Authentication Manager     │  → Authenticates the extracted credentials
└─────┬─────────────────────┘
      ▼
┌────────────────────┐
│  Security Context    │  → Stores the authenticated principal for the request
└─────┬───────────────┘
      ▼
┌─────────────┐
│  Controller  │  → Executes the request with enforced authorization
└─────────────┘
```

Each incoming request passes through the `JwtAuthenticationFilter`, which validates the token signature and expiration, loads the user's authorities, and populates the `SecurityContext` — enabling downstream `@PreAuthorize` checks at the controller and service layers.

---

## 🌳 Project Structure

```
hospital-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/hms/
│   │   │       ├── config/                 # Security, Swagger, and app configuration
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   └── OpenApiConfig.java
│   │   │       ├── controller/             # REST controllers
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── PatientController.java
│   │   │       │   ├── DoctorController.java
│   │   │       │   └── AppointmentController.java
│   │   │       ├── service/                # Business logic layer
│   │   │       │   ├── impl/
│   │   │       │   ├── AuthService.java
│   │   │       │   ├── PatientService.java
│   │   │       │   └── AppointmentService.java
│   │   │       ├── repository/             # Spring Data JPA repositories
│   │   │       │   ├── UserRepository.java
│   │   │       │   ├── PatientRepository.java
│   │   │       │   └── AppointmentRepository.java
│   │   │       ├── entity/                 # JPA entities
│   │   │       │   ├── User.java
│   │   │       │   ├── Role.java
│   │   │       │   ├── Permission.java
│   │   │       │   ├── Patient.java
│   │   │       │   ├── Doctor.java
│   │   │       │   └── Appointment.java
│   │   │       ├── dto/                    # Request/response DTOs
│   │   │       ├── security/               # JWT filters, providers, utils
│   │   │       │   ├── JwtAuthenticationFilter.java
│   │   │       │   ├── JwtService.java
│   │   │       │   └── UserDetailsServiceImpl.java
│   │   │       ├── exception/               # Global exception handling
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       └── HospitalManagementApplication.java
│   │   └── resources/
│   │       ├── db/migration/               # Flyway migration scripts
│   │       │   ├── V1__init_schema.sql
│   │       │   └── V2__seed_roles_permissions.sql
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       └── application-prod.yml
│   └── test/
│       └── java/com/hms/                   # Unit & integration tests
├── docker/
│   ├── Dockerfile
│   └── docker-compose.yml
├── .env.example
├── pom.xml
└── README.md
```

---

## ✅ Prerequisites

Ensure the following tools are installed before setting up the project:

| Tool | Minimum Version |
|---|---|
| Java (JDK) | 21+ |
| Maven | 3.9+ |
| Docker | 24.x+ |
| Docker Compose | 2.x+ |
| MySQL (if run locally without Docker) | 8.0+ |

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
  -p 3306:3306 \
  mysql:8.0
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

> 💡 Alternatively, use `docker-compose up -d` from the `docker/` directory to spin up both containers with a single command.

---

## ⚙️ Application Configuration

### Environment Variables

| Variable | Description | Example |
|---|---|---|
| `SPRING_DATASOURCE_URL` | JDBC connection string | `jdbc:mysql://localhost:3306/hospital_db` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `root` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `********` |
| `JWT_SECRET` | Secret key used to sign JWTs | `********` |
| `JWT_EXPIRATION` | Access token expiry (ms) | `3600000` |
| `JWT_REFRESH_EXPIRATION` | Refresh token expiry (ms) | `604800000` |
| `SERVER_PORT` | Application port | `8080` |

### Application Properties

<details>
<summary>📄 Click to expand <code>application.yml</code></summary>

```yaml
server:
  port: ${SERVER_PORT:8080}

spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL}
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
  flyway:
    enabled: true
    locations: classpath:db/migration

jwt:
  secret: ${JWT_SECRET}
  expiration: ${JWT_EXPIRATION:3600000}
  refresh-expiration: ${JWT_REFRESH_EXPIRATION:604800000}
```

</details>

### Database Configuration

- Hibernate's `ddl-auto` is set to `validate` — schema changes are **only** applied through Flyway, never auto-generated.
- Connection pooling is managed via HikariCP (Spring Boot default).

### Docker Configuration

- Multi-stage `Dockerfile` builds a slim runtime image using `eclipse-temurin:21-jre-alpine`.
- `docker-compose.yml` orchestrates the app and database services on a shared bridge network.

### Flyway Configuration

- Migrations are located at `src/main/resources/db/migration`.
- Applied automatically on application startup — no manual intervention required.

---

## 📡 API Endpoints

> Base URL: `http://localhost:8080/api/v1`

### 🔐 Authentication

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/register` | Register a new user |
| `POST` | `/auth/login` | Authenticate and receive JWT + refresh token |
| `POST` | `/auth/refresh-token` | Obtain a new access token using a refresh token |
| `POST` | `/auth/logout` | Invalidate the current session/token |

### 🧑‍🤝‍🧑 Patients

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/patients` | Retrieve all patients *(Admin/Doctor)* |
| `GET` | `/patients/{id}` | Retrieve a specific patient by ID |
| `POST` | `/patients` | Create a new patient record |
| `PUT` | `/patients/{id}` | Update patient details |
| `DELETE` | `/patients/{id}` | Remove a patient record *(Admin only)* |

### 👨‍⚕️ Doctors

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/doctors` | Retrieve all doctors |
| `GET` | `/doctors/{id}` | Retrieve a specific doctor by ID |
| `POST` | `/doctors` | Add a new doctor *(Admin only)* |
| `PUT` | `/doctors/{id}` | Update doctor details |
| `DELETE` | `/doctors/{id}` | Remove a doctor *(Admin only)* |

### 📅 Appointments

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/appointments` | Retrieve appointments (scoped by role) |
| `GET` | `/appointments/{id}` | Retrieve a specific appointment |
| `POST` | `/appointments` | Book a new appointment *(Patient)* |
| `PUT` | `/appointments/{id}` | Reschedule/update an appointment |
| `DELETE` | `/appointments/{id}` | Cancel an appointment |

### 📋 Medical Records

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/medical-records/{patientId}` | Retrieve a patient's medical history |
| `POST` | `/medical-records` | Create a new medical record *(Doctor)* |
| `PUT` | `/medical-records/{id}` | Update a medical record *(Doctor)* |

---

## 🐳 Docker Commands

A quick reference for common Docker operations used throughout this project's lifecycle.

| Command | Purpose |
|---|---|
| `docker build -t hms-backend .` | Build an image from the Dockerfile |
| `docker images` | List all local Docker images |
| `docker run -d -p 8080:8080 hms-backend` | Run a container from an image |
| `docker ps` | List running containers |
| `docker ps -a` | List all containers (including stopped) |
| `docker logs -f hms-backend` | Stream logs from a running container |
| `docker exec -it hms-backend sh` | Open an interactive shell inside a container |
| `docker start hms-backend` | Start a stopped container |
| `docker stop hms-backend` | Stop a running container |
| `docker restart hms-backend` | Restart a container |
| `docker rm hms-backend` | Remove a stopped container |
| `docker rmi hms-backend` | Remove an image |
| `docker network create hms-network` | Create a custom bridge network |
| `docker network ls` | List all Docker networks |
| `docker network inspect hms-network` | Inspect network details and connected containers |

---

## 🗃️ Database Migration

This project uses **Flyway** for reliable, version-controlled database schema management.

### Why Flyway?

Flyway ensures the database schema evolves predictably alongside the codebase, with every change tracked, auditable, and reproducible across all environments (local, staging, production).

### Migration Naming Convention

```
V<version>__<description>.sql

Examples:
V1__init_schema.sql
V2__seed_roles_permissions.sql
V3__add_prescription_table.sql
```

- `V` prefix denotes a **versioned** migration.
- The version number must be unique and strictly increasing.
- Double underscore (`__`) separates the version from the description.
- Description uses `snake_case` and should clearly summarize the change.

### Folder Structure

```
src/main/resources/db/migration/
├── V1__init_schema.sql
├── V2__seed_roles_permissions.sql
├── V3__add_prescription_table.sql
└── V4__add_indexes.sql
```

### ⚠️ Why Migrations Should Never Be Modified

Once a migration has been applied to any environment, **it must never be edited**. Flyway tracks applied migrations via checksums in the `flyway_schema_history` table — modifying a previously executed script will cause a **checksum mismatch error** and break deployments. Instead, any correction must be introduced as a **new** migration file (e.g., `V5__fix_patient_column_type.sql`).

---

## 🔒 Security

### JWT Flow

1. User submits credentials to `/auth/login`.
2. Server validates credentials against the stored (BCrypt-hashed) password.
3. Upon success, the server issues a short-lived **access token** and a long-lived **refresh token**.
4. The client includes the access token in the `Authorization: Bearer <token>` header on subsequent requests.
5. When the access token expires, the client calls `/auth/refresh-token` to obtain a new one without re-entering credentials.

### Authentication Flow

```
Login Request → Credential Validation → Token Generation → Token Returned to Client
```

### Authorization Flow

```
Incoming Request → JWT Validation → Load User Authorities → SecurityContext Population → @PreAuthorize Check → Controller Execution
```

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

### Permission Mapping

| Role | Sample Permissions |
|---|---|
| `ADMIN` | `USER_MANAGE`, `ROLE_MANAGE`, `PERMISSION_MANAGE`, `DOCTOR_MANAGE`, `PATIENT_MANAGE`, `ANALYTICS_VIEW` |
| `DOCTOR` | `PATIENT_READ`, `APPOINTMENT_MANAGE`, `MEDICAL_RECORD_WRITE`, `PRESCRIPTION_WRITE` |
| `PATIENT` | `APPOINTMENT_BOOK`, `PROFILE_UPDATE`, `PRESCRIPTION_READ` |

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

| Exception | HTTP Status |
|---|---|
| `ResourceNotFoundException` | `404 Not Found` |
| `BadCredentialsException` | `401 Unauthorized` |
| `AccessDeniedException` | `403 Forbidden` |
| `MethodArgumentNotValidException` | `400 Bad Request` |
| `DataIntegrityViolationException` | `409 Conflict` |
| `Exception` (fallback) | `500 Internal Server Error` |

---

## 🚢 Deployment

- The application is packaged as a Docker image and can be deployed to any container orchestration platform (Kubernetes, ECS, Docker Swarm).
- Recommended production setup includes a managed MySQL instance, environment-based secret management (e.g., Vault, AWS Secrets Manager), and a reverse proxy (Nginx) with TLS termination.
- CI/CD pipelines (GitHub Actions placeholder) can automate build, test, image push, and deployment steps.

---

## 🔮 Future Enhancements

- 📧 Email/SMS notifications for appointment reminders
- 📊 Advanced analytics dashboard for administrators
- 🧾 Billing and invoicing module
- 🗓️ Doctor availability calendar with time-slot booking
- 🌐 Multi-tenancy support for hospital chains
- 📱 Mobile application integration
- 🧪 Lab test result management

---

## 🤝 Contribution Guide

Contributions are welcome! To contribute:

1. **Fork** the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m "Add: your feature description"`
4. Push to your branch: `git push origin feature/your-feature-name`
5. Open a **Pull Request** describing your changes

Please ensure all new code is covered by unit tests and follows existing code style conventions.

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

<div align="center">

**Your Name**

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/<your-username>)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/<your-profile>)
[![Email](https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white)](2005indrajitmaity@gmail.com)

</div>

---

<div align="center">

⭐ **If you find this project useful, consider giving it a star!** ⭐

</div>
