# 🚗 RideFlow_Backend-Microservice

A production-style, scalable Uber-like backend system built using a **microservices architecture** with Spring Boot. The system covers the complete ride-hailing flow — authentication, ride booking, real-time driver location tracking, reviews, and service discovery.

---

## 📋 Table of Contents

- [Architecture Overview](#-architecture-overview)
- [Services](#-services)
- [Tech Stack](#-tech-stack)
- [Domain Models](#-domain-models)
- [API Reference](#-api-reference)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Inter-Service Communication](#-inter-service-communication)
- [Features](#-features)
- [Upcoming Features](#-upcoming-features)
- [Developer](#-developer)

---

## 🏗 Architecture Overview

```
┌─────────────────────────────────────────────────────────────────────┐
│                        Client (Mobile / Web)                        │
└────────────────────────────┬────────────────────────────────────────┘
                             │ REST / WebSocket
          ┌──────────────────┼──────────────────┐
          ▼                  ▼                  ▼
  ┌───────────────┐  ┌──────────────┐  ┌────────────────┐
  │  Auth Service │  │Booking Service│  │ Review Service │
  │   port: 8081  │  │  port: 8001  │  │   port: 8083   │
  └───────┬───────┘  └──────┬───────┘  └───────┬────────┘
          │                 │                   │
          │         ┌───────▼────────┐          │
          │         │Location Service│          │
          │         │   port: 7777   │          │
          │         └───────┬────────┘          │
          │                 │                   │
          └─────────────────┼───────────────────┘
                            ▼
               ┌────────────────────────┐
               │  Eureka Server (8761)  │  ← Service Discovery
               └────────────────────────┘
                            │
               ┌────────────▼───────────┐
               │    Entity Service      │  ← Shared Domain Library
               │  (Maven Local Artifact)│
               └────────────────────────┘
```

**Key communication patterns:**
- **Retrofit2** — Booking Service → Location Service (async HTTP)
- **Apache Kafka** — Event-driven messaging between services
- **Spring WebSocket** — Real-time ride-request push to drivers
- **Netflix Eureka** — Dynamic service registration & discovery

---

## 📦 Services

| Service | Directory | Port | Role |
|---|---|---|---|
| **Eureka Server** | `UberProject-ServiceDiscovery-EurekaServer` | `8761` | Service discovery & registry |
| **Entity Service** | `UberProject-EntityService` | `8082` | Shared domain models (published as Maven artifact) |
| **Auth Service** | `UberProject-AuthService` | `8081` | Passenger sign-up, sign-in, JWT issuance & validation |
| **Booking Service** | `Uber-Booking-service` | `8001` | Ride creation, driver assignment, Kafka events |
| **Location Service** | `UberProject---Location-Service` | `7777` | Driver geo-location via Redis GEO commands |
| **Review Service** | `UberProject-ReviewService` | `8083` | CRUD for post-ride reviews and ratings |

### 🔍 Service Details

#### 1. Eureka Server
- Netflix Eureka discovery server.
- All other services register themselves here at startup.
- Dashboard available at `http://localhost:8761`.

#### 2. Entity Service
- A **library** (not a standalone web service) published to Maven Local.
- Contains all shared JPA entities: `Passenger`, `Driver`, `Car`, `Booking`, `Review`, `OTP`, `ExactLocation`, `NamedLocation`.
- Also includes enums: `BookingStatus`, `DriverApprovalStatus`, `CarType`, `Color`.
- Uses **Flyway** for versioned database migrations.
- Consumed by Auth, Booking, and Review services via `mavenLocal()`.

#### 3. Auth Service
- Handles passenger registration and login.
- Issues **JWT tokens** stored as HTTP-only cookies.
- Uses **Spring Security** with `BCrypt` password hashing.
- Validates tokens for downstream services via the `/validate` endpoint.

#### 4. Booking Service
- Core ride-booking orchestration service.
- On booking creation: queries Location Service for nearby drivers asynchronously (Retrofit2).
- Sends ride requests to drivers via **WebSocket** (UberSocketApi).
- Publishes and consumes events via **Apache Kafka**.
- Registered with Eureka for dynamic discovery.

#### 5. Location Service
- Stores and retrieves driver GPS coordinates using **Redis GEO** (via Jedis).
- Provides a nearby-drivers lookup within a configurable radius.
- Registered with Eureka.

#### 6. Review Service
- Full CRUD for ride reviews.
- Uses **Flyway** for schema migrations.
- Depends on Entity Service as a Maven artifact.
- Exposes Spring Actuator endpoints for health monitoring.

---

## 🛠 Tech Stack

| Category | Technology |
|---|---|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.x |
| **Security** | Spring Security, JWT (jjwt 0.12.5), BCrypt |
| **Database** | MySQL |
| **ORM** | Spring Data JPA, Hibernate |
| **Migrations** | Flyway 9.22.x |
| **Caching / Geo** | Redis, Jedis 5.1.2 |
| **Messaging** | Apache Kafka (spring-kafka 3.1.4) |
| **Service Discovery** | Netflix Eureka (Spring Cloud 2023.0.1) |
| **HTTP Client** | Retrofit2 2.4.0 + OkHttp 4.9.0 |
| **Real-time** | Spring WebSocket |
| **Monitoring** | Spring Boot Actuator |
| **Build Tool** | Gradle |
| **Boilerplate** | Lombok |
| **Testing** | JUnit 5, Mockito |
| **Dev Tools** | Spring Boot DevTools |

---

## 🗂 Domain Models

All entities live in **Entity Service** and are shared across services.

```
Passenger          Driver
├── id             ├── id
├── name           ├── name
├── email          ├── licenseNumber
├── password       ├── phoneNumber
└── bookings       ├── aadharCard
                   ├── car (Car)
Car                ├── driverApprovalStatus
├── id             ├── lastKnownLocation (ExactLocation)
├── licensePlate   ├── home (ExactLocation)
├── color (Color)  ├── activeCity
├── carType        ├── rating
└── driver         ├── isAvailable
                   └── bookings

Booking            Review
├── id             ├── id
├── bookingStatus  ├── content
├── startTime      ├── rating
├── endTime        ├── booking (Booking)
├── totalDistance  ├── createdAt
├── driver         └── updatedAt
├── passenger
└── startLocation (ExactLocation)

ExactLocation      OTP
├── id             ├── id
├── latitude       ├── code
└── longitude      └── expiresAt
```

**Enums:** `BookingStatus` · `DriverApprovalStatus` · `CarType` · `Color`

---

## 📡 API Reference

### Auth Service — `http://localhost:8081`

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `POST` | `/api/v1/auth/signup/passenger` | Register a new passenger | None |
| `POST` | `/api/v1/auth/signin/passenger` | Sign in; sets `JwtToken` cookie | None |
| `GET` | `/api/v1/auth/validate` | Validate JWT token from cookie | JWT Cookie |

**Sign-up request body:**
```json
{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "password": "securePassword"
}
```

**Sign-in request body:**
```json
{
  "email": "jane@example.com",
  "password": "securePassword"
}
```

---

### Booking Service — `http://localhost:8001`

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `POST` | `/api/v1/booking` | Create a new ride booking | JWT |
| `POST` | `/api/v1/booking/{bookingId}` | Update booking (assign driver) | JWT |

**Create booking request body:**
```json
{
  "passengerId": 1,
  "startLocation": {
    "latitude": 28.6139,
    "longitude": 77.2090
  }
}
```

**Update booking request body:**
```json
{
  "driverId": 5
}
```

---

### Location Service — `http://localhost:7777`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/location/drivers` | Save/update a driver's location |
| `POST` | `/api/location/nearby/drivers` | Get nearby available drivers |

**Save driver location request body:**
```json
{
  "driverId": 5,
  "latitude": 28.6139,
  "longitude": 77.2090
}
```

**Nearby drivers request body:**
```json
{
  "latitude": 28.6139,
  "longitude": 77.2090
}
```

---

### Review Service — `http://localhost:8083`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/reviews` | Create a review for a completed booking |
| `GET` | `/api/v1/reviews` | Get all reviews |
| `GET` | `/api/v1/reviews/{reviewId}` | Get review by ID |
| `PUT` | `/api/v1/reviews/{reviewId}` | Update a review |
| `DELETE` | `/api/v1/reviews/{reviewId}` | Delete a review |

**Create review request body:**
```json
{
  "bookingId": 10,
  "content": "Great ride, very smooth!",
  "rating": 4.5
}
```

---

## 🚀 Getting Started

### Prerequisites

| Tool | Version |
|---|---|
| Java | 17+ |
| Gradle | 8+ |
| MySQL | 8+ |
| Redis | 7+ |
| Apache Kafka | 3.x |

### 1. Start Infrastructure

```bash
# MySQL — create the database
mysql -u root -p -e "CREATE DATABASE Uber_Db;"

# Redis (default port 6379)
redis-server

# Kafka — start Zookeeper then broker
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```

### 2. Publish Entity Service to Maven Local

The Entity Service is a shared library consumed by Auth, Booking, and Review services. It must be published before those services can build.

```bash
cd UberProject-EntityService
./gradlew publishToMavenLocal
```

### 3. Start Services (in order)

**a) Eureka Server** — start first so all services can register.
```bash
cd UberProject-ServiceDiscovery-EurekaServer
./gradlew bootRun
# Dashboard: http://localhost:8761
```

**b) Auth Service**
```bash
cd UberProject-AuthService
./gradlew bootRun
# Running on: http://localhost:8081
```

**c) Location Service**
```bash
cd UberProject---Location-Service
./gradlew bootRun
# Running on: http://localhost:7777
```

**d) Booking Service**
```bash
cd Uber-Booking-service
./gradlew bootRun
# Running on: http://localhost:8001
```

**e) Review Service**
```bash
cd UberProject-ReviewService
./gradlew bootRun
# Running on: http://localhost:8083
```

---

## ⚙️ Configuration

Each service reads configuration from `application.properties` or `application.yml`. Below are the key properties to set before running.

### Auth Service (`UberProject-AuthService/src/main/resources/application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Uber_Db
spring.datasource.username=<your_db_user>
spring.datasource.password=<your_db_password>
server.port=8081

# JWT
jwt.secret=<your_256bit_or_longer_secret_key>
jwt.expiry=3600
cookie.expiry=3600
```

### Booking Service (`Uber-Booking-service/src/main/resources/application.yml`)

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/Uber_Db_Local
    username: root
    password: ${mysql_password}   # set env var mysql_password
  kafka:
    bootstrap-servers: localhost:9092
server:
  port: 8001
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

### Location Service (`UberProject---Location-Service/src/main/resources/application.yml`)

```yaml
spring:
  application:
    name: LocationService
  data:
    redis:
      host: localhost
      port: 6379
server:
  port: 7777
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

### Entity Service (`UberProject-EntityService/src/main/resources/application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Uber_Db
spring.datasource.username=<your_db_user>
spring.datasource.password=<your_db_password>
spring.flyway.url=jdbc:mysql://localhost:3306/Uber_Db
spring.flyway.user=<your_db_user>
spring.flyway.password=<your_db_password>
server.port=8082
```

### Eureka Server (`UberProject-ServiceDiscovery-EurekaServer/src/main/resources/application.yml`)

```yaml
spring:
  application:
    name: UberServiceDiscovery
server:
  port: 8761
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

> ⚠️ **Never commit real credentials.** Use environment variables or a secrets manager in production.

---

## 🔗 Inter-Service Communication

```
Booking Service
    │
    ├──[Retrofit2 async HTTP]──► Location Service  (GET nearby drivers)
    │
    ├──[Retrofit2 async HTTP]──► UberSocket (WebSocket server)  (push ride request to driver)
    │
    └──[Kafka Producer/Consumer]──► Event bus  (ride events)

Auth Service
    └──[JWT Cookie]──► All services  (stateless token validation)

All Eureka Clients
    └──[Eureka client]──► Eureka Server  (registration & heartbeat)
```

---

## ✅ Features

- 🔐 **Secure Authentication** — Passenger sign-up & login with JWT (HTTP-only cookie) + BCrypt password hashing
- 🗺 **Real-time Driver Location** — GPS coordinates stored and queried in Redis using GEO commands
- 📍 **Ride Booking Flow** — Create booking → query nearby drivers → push ride request via WebSocket → assign driver
- 📨 **Event-driven Architecture** — Kafka messaging for decoupled, asynchronous service communication
- 🔎 **Service Discovery** — Netflix Eureka for dynamic service registration and load balancing
- 🛡 **Role-based Access** — Spring Security with configurable security filters
- 🗃 **Versioned DB Migrations** — Flyway handles schema evolution across environments
- 🧩 **Shared Domain Library** — Entity Service published as a Maven artifact, reused across services
- ⭐ **Review System** — Full CRUD with rating validation and booking association
- 📊 **Health Monitoring** — Spring Boot Actuator endpoints on Review Service

---

## 📌 Upcoming Features

- [ ] **Payment Service** — Integrated payment flow (Stripe / Razorpay)
- [ ] **Notification Service** — Email and SMS updates via Twilio / SendGrid
- [ ] **API Gateway** — Centralized routing, rate limiting, and authentication
- [ ] **Driver Service** — Driver onboarding, approval workflow, and availability management
- [ ] **OTP Service** — Phone/email OTP verification for passengers and drivers
- [ ] **Admin Dashboard** — Driver approval and system monitoring
- [ ] **Docker Compose** — One-command local environment setup
- [ ] **CI/CD Pipeline** — Automated build, test, and deployment

---

## 🧪 Running Tests

```bash
# Run tests for any individual service
cd <service-directory>
./gradlew test
```

---

## 👨‍💻 Developer

**Jatin Hati**
💻 Engineering Student | Aspiring Full Stack Developer
🔗 GitHub: [@jatinhati](https://github.com/jatinhati)

