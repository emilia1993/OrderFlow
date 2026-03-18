# 🚀 **OrderFlow – Enterprise Spring Boot REST API**

Advanced backend system for order management built with Spring Boot 3, 
featuring secure REST APIs, event-driven architecture (Kafka), concurrency control, and full test coverage.

## 📌 Overview

OrderFlow is a production-style backend application designed to manage:

- Orders (Testata)

- Order Lines (Righe)

- Payments (Pagamenti)

The project goes beyond a simple CRUD system by integrating:

🔐 Spring Security (role-based access)

📄 OpenAPI / Swagger documentation

📡 Apache Kafka (event-driven architecture)

⏱ Scheduled processing

🔄 Optimistic Locking (concurrency control)

🧪 Full testing strategy (unit + integration)

---

## 🏗 Architecture

Layered + Event-Driven Architecture:

Controller → Service → Repository → Database
                ↓
               Kafka 
                ↓
             Scheduler

### Responsibilities:

* Controller:
  - Exposes REST endpoints
  - Handles HTTP requests/responses
  - DTO validation

* Service:
  - Business logic
  - Transaction management
  - DTO ↔ Entity mapping

* Repository:
  - Data access via Spring Data JPA

* Kafka Layer:
  - Async communication between components

* Scheduler:
  - Periodic processing of buffered messages

---

## 🔐 Security (Spring Security)

The API is secured using HTTP Basic Authentication with role-based access.

### 👤 Users

| Username | Password | Role  |
|----------|----------|-------|
| user     | password | USER  |
| admin    | password | ADMIN |


### 🔒 Authorization Rules
Access control is implemented using Spring Security with role-based authorization.

| Endpoint                  | Access       |
|---------------------------|--------------|
| GET /ordini               | USER, ADMIN  |
| POST /ordini              | USER, ADMIN  |
| PUT /ordini/{id}          | USER, ADMIN  |
| PATCH /ordini/{id}/stato  | USER, ADMIN  |
| DELETE /ordini/{id}       | ADMIN only   |
| Swagger UI                | Public       |

---

## 📄 API Documentation (Swagger)

Interactive API documentation available at:

http://localhost:8080/swagger-ui.html

OpenAPI is configured via:

`OpenAPI customOpenAPI()`

---

## 🌐 REST API

Base Path
/ordini

* GET /ordini
  Retrieve all orders

* POST /ordini
  Create a new order

  {
  "descrizione": "Ordine di test",
  "dataConsegna": "2026-01-20",
  "statoOrdine": "IN_PREPARAZIONE"
  }

* PUT /ordini/{id}
  Update full order

* PATCH /ordini/{id}/stato
  Update only order status

  "SPEDITO"

* DELETE /ordini/{id}
  Delete order (ADMIN only)
  Returns:
  - 204 No Content → success
  - 404 Not Found → if not exists
  - 409 Conflict → optimistic locking failure

---

## 🧩 DTO & Validation Layer

DTOs are used to decouple API from persistence layer.

Validation Examples:
* @NotBlank → descrizione
* @FutureOrPresent → dataConsegna
* @Min(0) → prezzo

Example Error Response:
{
"descrizione": "Description is required",
"dataConsegna": "Delivery date is required"
}

---

## 🔄 Concurrency Control

Uses Optimistic Locking via:

`@Version`

Guarantees:
* Prevents lost updates
* Detects concurrent modifications
* Ensures data consistency

--- 

## 📡 Event-Driven Architecture (Kafka)

* Producer
  OrdineProducer

  - Sends orders to topic:
    imieiordini1

* Consumer
  OrdineConsumer

  - Receives messages from Kafka
  - Persists orders into database

* Collector + Scheduler
  OrdineCollector

  - Buffers incoming messages
  - Processes them every 5 minutes:
    `@Scheduled(fixedRate = 5 * 60 * 1000)`

--- 

## 🗄 Database

* H2 in-memory database

* Auto schema creation

**H2 Console**

http://localhost:8080/h2-console

| Property   | Value              |
|------------|--------------------|
| JDBC URL   | jdbc:h2:mem:testdb |
| User       | sa                 |
| Password   | password           |

--- 

## 🧪 Testing Strategy

Comprehensive testing implemented:

✔ Unit Tests
  Service layer (Mockito)

✔ Integration Tests
  Controller (MockMvc)

✔ Repository Tests
  JPA queries

✔ Validation Tests
   DTO constraints

✔ Exception Handling Tests

Run tests:
`mvn test`

--- 

## 🪵 Logging

Configured using:
* SLF4J
* Logback

Outputs:
* Console
* File → log.txt

---

## 🐳 Docker

* Build image:
  `docker build -t orderflow .`

* Run container:
  `docker run -p 8080:8080 orderflow`

---

## ▶️ Run Locally

Build:
`mvn clean package`

Run:
`java -jar target/orderflow-1.0.1.jar`

Or using Maven:
`mvn spring-boot:run`

---

## 📦 Tech Stack

* Java 17
* Spring Boot 3
* Spring Web
* Spring Data JPA
* Spring Security
* Spring Kafka
* Spring Scheduler
* H2 Database
* Lombok
* Maven
* Docker
* OpenAPI / Swagger
* JUnit 5 / Mockito

---

## 📁 Project Structure

com.github.orderflow
├── controller
├── service
├── repository
├── entity
├── model (DTO)
├── exception
├── config

---

## 🛣 Roadmap

* JWT Authentication
* MapStruct (DTO mapping)
* Dedicated APIs for Righe/Pagamenti
* Kafka persistence & retry
* CI/CD pipeline
* Cloud deployment

---

## 👨‍💻 Author

Personal project focused on:

* Backend architecture
* Distributed systems
* Event-driven design
* Concurrency handling
* Enterprise Java development