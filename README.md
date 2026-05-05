## 🚗 RideFlowProject - Microservices Architecture

This repository contains a set of microservices for a simplified RideFlow-like backend system. It follows a scalable and modular architecture using Spring Boot, Flyway for DB migrations, and Spring Security for authentication.

## 📁 Project Structure

RideFlowProject-AuthService/
RideFlowProject-EntityService/
RideFlowProject-ReviewService/

AuthService – Manages user authentication, registration, and secure access.

EntityService – Manages core data models like passengers and drivers.

ReviewService – Handles user reviews and ratings for completed rides.

## 🛠 Tech Stack and Libraries

☕ Backend Framework
Spring Boot – Primary framework for building RESTful microservices.

## 🔐 Security \& Authentication

Spring Security – Manages role-based access, authentication \& authorization.

JWT (JSON Web Token) – Stateless authentication mechanism.

BCrypt – Password hashing mechanism for secure storage.

## 🗃️ Database \& ORM

MySQL – Relational database used across services.

Spring Data JPA – Abstraction over Hibernate for ORM.

Hibernate – Underlying JPA implementation used for object-relational mapping.

Flyway – Version-controlled database migrations across environments.

## 🛠 Development Tools

Gradle – Project and dependency management.

Lombok – Reduces boilerplate code with annotations like @Getter, @Builder, etc.

ModelMapper / MapStruct (optional) – DTO and entity transformation (if used).

## 🧪 Testing Libraries

JUnit 5 – Unit testing framework.

Mockito – For mocking services and repositories.

🌐 Inter-Service Communication
RESTful APIs – Services communicate via REST endpoints.

(Optional) OpenFeign / RestTemplate – For service-to-service communication if implemented.

🐳 Deployment \& Environment
Docker (optional) – For containerizing and deploying services.

Spring Boot DevTools – Enables hot reloading during development.

## 🚧 Getting Started

Each service runs independently with its own DB schema managed via Flyway.

Prerequisites
Java 17+

Gradle 3+

MySQL (



cd RideFlowProject-AuthService
./mvnw spring-boot:run
Repeat for each service.

## 🔐 Note: You must configure environment variables or application.properties for database, JWT secrets, and service-specific configs.

## ✅ Features

Secure signup/login system using JWT

Modular microservice architecture

Versioned database migrations using Flyway

Clean layered architecture using DTOs, services, and repositories

Integrated unit testing support

## 📌 Upcoming Features

RideService (ride booking and tracking)

PaymentService (integrated payment flow)

NotificationService (email/SMS updates)

## API Gateway + Eureka Discovery (for service orchestration)

## 👨‍💻 Developer

Jatin Hati
💻 Engineering Student | Aspiring Full Stack Developer
📍 GitHub: @jatinhati

