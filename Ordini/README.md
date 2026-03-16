# Ordini – Spring Boot REST API

> REST API per la gestione degli ordini, sviluppata come progetto personale per lo studio di Spring Boot, JPA, Kafka e architetture backend moderne.

---

## Overview

**Ordini** è un backend REST sviluppato in **Spring Boot** che consente la gestione di:
- ordini (testata)
- righe d’ordine
- pagamenti

Il progetto integra:
- **Spring Data JPA** per la persistenza
- **H2 in-memory database**
- **Apache Kafka** per la messaggistica asincrona
- **Scheduler** per l’elaborazione periodica dei messaggi
- **Optimistic Locking** per la gestione della concorrenza

Il codice è versionato tramite **GitHub** ed è pensato come base evolutiva.

---

## Features

- API REST (GET, POST, DELETE) per gli ordini
- Relazioni JPA tra ordini, righe e pagamenti
- Optimistic Locking su eliminazione ordini
- Database H2 con schema SQL
- Logging su console e file
- Producer e Consumer Kafka
- Scheduler per gestione batch dei messaggi
- Docker support

---

## Architettura

Architettura a livelli:

Controller → Model → Entity → Repository → Database
↓
Kafka
↓
Scheduler

- **Controller**: espone le API REST
- **Model**: DTO per scambio dati
- **Entity**: mapping JPA
- **Repository**: accesso ai dati
- **Service**: integrazione Kafka e logica asincrona

---

## Tech Stack

- Java 17
- Spring Boot 3.5.7
- Spring Web
- Spring Data JPA
- Spring Kafka
- Spring Scheduler
- H2 Database
- Lombok
- SLF4J + Logback
- Maven
- Docker

---

## Project Structure

com.example.Ordini
├── controller
│ └── TestataOrdineController.java
├── service
│ ├── OrdineProducer.java
│ ├── OrdineCollector.java
│ └── OrdineConsumer.java
├── model
│ ├── TestataOrdine.java
│ ├── RigheOrdine.java
│ └── Pagamenti.java
├── entity
│ ├── TestataOrdine.java
│ ├── RigheOrdine.java
│ └── Pagamenti.java
├── repository
│ ├── TestataOrdineRepository.java
│ ├── RigheOrdineRepository.java
│ └── PagamentiRepository.java
├── enumModel
│ └── StatoOrdine.java
└── resources
├── application.properties
├── application.yml
├── schema.sql
└── logback-spring.xml

---

## Database Model

### TestataOrdine
- `id`
- `descrizione`
- `dataConsegna`
- `statoOrdine`
- `version` (Optimistic Lock)
- OneToMany → `RigheOrdine`
- OneToMany → `Pagamenti`

### RigheOrdine
- `id`
- `cod_prodotto`
- `prezzo`
- ManyToOne → `TestataOrdine`

### Pagamenti
- `id`
- `dataPagamento`
- ManyToOne → `TestataOrdine`

---

## REST API

### Base path
/ordini

### GET /ordini
Restituisce tutti gli ordini.

### POST /ordini
Crea un nuovo ordine.

```json
{
  "descrizione": "Ordine di esempio",
  "dataConsegna": "2026-01-20",
  "statoOrdine": "CREATO"
}
```

### DELETE /ordini/{id}
Elimina un ordine tramite ID con supporto a Optimistic Locking.


🧩 Kafka Integration
Il progetto include integrazione con Apache Kafka.

Producer
OrdineProducer

Invia un ordine al topic Kafka imieiordini1

Consumer / Collector
OrdineCollector

Ascolta il topic imieiordini1

Accumula i messaggi in memoria

Elabora i messaggi tramite scheduler

Scheduler
Frequenza: ogni 5 minuti

Logga gli ordini ricevuti

Svuota il buffer dopo l’elaborazione


🔐 Concurrency Control
La cancellazione degli ordini utilizza Optimistic Locking (@Version)
per prevenire conflitti in ambienti concorrenti.


🧪 H2 Console
Database in-memory.

http://localhost:8080/h2-console

Credenziali

JDBC URL: jdbc:h2:mem:testdb

Username: sa

Password: password


🪵 Logging
Configurato tramite Logback:

Console

File log.txt

Livello default: INFO


▶️ Run Locally
mvn clean package
java -jar target/Ordini-0.0.1-SNAPSHOT.jar

Oppure:
mvn spring-boot:run


🐳 Docker
docker build -t ordini-app .
docker run -p 8080:8080 ordini-app


🔖 Versioning
Versionamento tramite GitHub

Versione corrente: 0.0.1-SNAPSHOT

Release e tag Git consigliati

CHANGELOG suggerito per le versioni future


🛣 Roadmap
 PUT / UPDATE ordini

 API dedicate per righe e pagamenti

 DTO + Mapper

 Persistenza messaggi Kafka

 Test di integrazione

 Spring Security


👤 Author
Progetto personale per l’approfondimento di:
Spring Boot, REST API, JPA, Kafka, concorrenza e containerizzazione.