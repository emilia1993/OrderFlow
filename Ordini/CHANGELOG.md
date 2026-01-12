# Changelog

Tutte le modifiche rilevanti a questo progetto saranno documentate in questo file.

Il formato è basato su [Keep a Changelog](https://keepachangelog.com/it/1.0.0/)
e il progetto segue il versionamento tramite **GitHub**.

---

## [Unreleased]
### Added
- Base per integrazione Kafka (producer, consumer, scheduler)
- Struttura modulare Controller / Service / Repository

---

## [0.0.1-SNAPSHOT] – 2026-01-XX
### Added
- Progetto Spring Boot iniziale
- API REST per la gestione degli ordini
  - `GET /ordini`
  - `POST /ordini`
  - `DELETE /ordini/{id}`
- Entity JPA:
  - `TestataOrdine`
  - `RigheOrdine`
  - `Pagamenti`
- Relazioni JPA:
  - OneToMany tra TestataOrdine e RigheOrdine
  - OneToMany tra TestataOrdine e Pagamenti
- Optimistic Locking su TestataOrdine tramite `@Version`
- Repository Spring Data JPA per tutte le entity
- Database H2 in-memory
- Script di inizializzazione `schema.sql`
- Logging con Logback:
  - output su console
  - output su file `log.txt`
- Dockerfile per containerizzazione dell’applicazione
- Configurazione Kafka:
  - Producer (`OrdineProducer`)
  - Consumer / Collector (`OrdineCollector`)
  - Scheduler per elaborazione periodica dei messaggi

### Changed
- Separazione tra Model (DTO) ed Entity JPA
- Introduzione del layer Service per la messaggistica Kafka

### Notes
- Il database è in-memory e i dati non persistono tra i riavvii
- Le API implementate sono limitate a GET, POST e DELETE

---

## Tipologie di modifiche
- **Added**: nuove funzionalità
- **Changed**: modifiche a funzionalità esistenti
- **Deprecated**: funzionalità deprecate
- **Removed**: funzionalità rimosse
- **Fixed**: bug fix
- **Security**: correzioni di sicurezza
