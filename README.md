# NerdLab Microservice Template

Production-ready Spring Boot microservice scaffold using **Hexagonal Architecture (Ports & Adapters)**.

## Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    Adapters (In)                        │
│  ┌──────────────┐                                      │
│  │ REST Controllers │ ─── DTOs / Validation            │
│  └──────┬───────┘                                      │
│         │                                              │
│  ┌──────▼───────────────────────────────────────┐      │
│  │           Application Layer                   │      │
│  │  ┌──────────────────────────────────────┐    │      │
│  │  │         Use Case Services            │    │      │
│  │  └──────────────────────────────────────┘    │      │
│  └──────┬───────────────────────────────────┘   │      │
│         │                                       │      │
│  ┌──────▼───────────────────────────────────┐   │      │
│  │           Domain Layer                    │   │      │
│  │  Entities │ Value Objects │ Port Interfaces│  │      │
│  └──────────────────────────────────────────┘   │      │
│         │                                       │      │
│  ┌──────▼───────────────────────────────────┐   │      │
│  │           Adapters (Out)                  │   │      │
│  │  Persistence │ Messaging │ External APIs  │   │      │
│  └──────────────────────────────────────────┘   │      │
└─────────────────────────────────────────────────────────┘
```

## Project Structure

```
src/main/java/com/nerdlab/microservice/
├── domain/
│   ├── model/              # Entities, Value Objects (pure Java)
│   └── port/
│       ├── in/             # Use case interfaces (inbound ports)
│       └── out/            # Repository/client contracts (outbound ports)
├── application/
│   └── service/            # Use case implementations
├── adapter/
│   ├── in/
│   │   └── web/            # REST controllers, DTOs, mappers
│   └── out/
│       ├── persistence/    # JPA entities, repositories, adapters
│       ├── messaging/      # Event publisher stub (Kafka/RabbitMQ)
│       └── client/         # External API client stub
├── config/                 # Spring configuration (Security, JPA, OpenAPI, Logging)
├── exception/              # Global exception handling
├── util/                   # Shared utilities (ApiResponse)
└── MicroserviceApplication.java
```

## Tech Stack

| Component         | Technology                          |
|-------------------|-------------------------------------|
| Framework         | Spring Boot 3.5                     |
| Language          | Java 21                             |
| Architecture      | Hexagonal (Ports & Adapters)        |
| Database          | PostgreSQL (prod) / H2 (dev/test)   |
| Migrations        | Flyway                              |
| Security          | Spring Security (disabled by default) |
| API Docs          | OpenAPI 3 / Swagger UI              |
| Observability     | Actuator + Micrometer + Prometheus  |
| Resilience        | Resilience4j                        |
| Testing           | JUnit 5 + Mockito + Testcontainers  |
| Containerization  | Docker + Docker Compose             |
| CI/CD             | GitHub Actions                      |
| Code Coverage     | JaCoCo                              |

## Quick Start

### Prerequisites

- Java 21+
- Maven 3.9+ (or use the included `mvnw` wrapper)
- Docker & Docker Compose (optional, for containerized deployment)

### Run Locally (dev profile with H2)

```bash
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080` with an in-memory H2 database.

### Run with Docker Compose (prod profile with PostgreSQL)

```bash
docker-compose up --build
```

### Run Tests

```bash
./mvnw clean verify
```

## API Endpoints

### Health

| Method | Endpoint        | Description     |
|--------|-----------------|-----------------|
| GET    | `/api/v1/ping`  | Health check    |
| GET    | `/api/v1/hello` | Hello world     |

### Users (CRUD)

| Method | Endpoint             | Description      |
|--------|----------------------|------------------|
| POST   | `/api/v1/users`      | Create user      |
| GET    | `/api/v1/users`      | List all users   |
| GET    | `/api/v1/users/{id}` | Get user by ID   |
| PUT    | `/api/v1/users/{id}` | Update user      |
| DELETE | `/api/v1/users/{id}` | Delete user      |

### API Response Format

All endpoints return a standardized response:

```json
{
  "data": {},
  "message": "success",
  "timestamp": "2024-01-01T00:00:00",
  "errors": []
}
```

## API Documentation

Swagger UI is available at: `http://localhost:8080/swagger-ui.html`

OpenAPI spec: `http://localhost:8080/v3/api-docs`

## Configuration Profiles

| Profile | Database   | Description                    |
|---------|------------|--------------------------------|
| `dev`   | H2 (memory)| Default. H2 console at `/h2-console` |
| `test`  | H2 (memory)| Used for automated tests       |
| `prod`  | PostgreSQL | Production with connection pool |

### Environment Variables (prod)

| Variable      | Default       | Description          |
|---------------|---------------|----------------------|
| `DB_HOST`     | `localhost`   | Database host        |
| `DB_PORT`     | `5432`        | Database port        |
| `DB_NAME`     | `microservice`| Database name        |
| `DB_USERNAME` | `postgres`    | Database username    |
| `DB_PASSWORD` | `postgres`    | Database password    |

## Observability

- **Health**: `GET /actuator/health`
- **Metrics**: `GET /actuator/metrics`
- **Prometheus**: `GET /actuator/prometheus`
- **Info**: `GET /actuator/info`

### Structured Logging

Every request includes a correlation ID (`traceId`) via the `X-Trace-Id` header for distributed tracing.

## Security

Spring Security is included but **all endpoints are open by default**. To enable authentication:

1. Modify `SecurityConfig.java` to restrict endpoints
2. Add JWT filter or OAuth2 resource server configuration

## CI/CD

GitHub Actions workflows:

- **CI Pipeline** (`ci.yml`): Build, test, and publish Docker image
- **Quality Gate** (`sonar.yml`): SonarQube analysis
- **Security Scan** (`trivy-scan.yml`): Trivy vulnerability scanning

## Extending the Template

### Add a New Domain Entity

1. Create domain model in `domain/model/`
2. Define use case interfaces in `domain/port/in/`
3. Define repository port in `domain/port/out/`
4. Implement service in `application/service/`
5. Create JPA entity in `adapter/out/persistence/entity/`
6. Create JPA repository in `adapter/out/persistence/repository/`
7. Create persistence adapter in `adapter/out/persistence/`
8. Create DTOs and controller in `adapter/in/web/`
9. Add Flyway migration in `resources/db/migration/`

### Add Messaging (Kafka/RabbitMQ)

Replace the stub in `adapter/out/messaging/EventPublisher.java` with your actual messaging implementation.

### Add External API Client

Replace the stub in `adapter/out/client/ExternalApiClient.java` with Feign or WebClient implementation.

## Design Principles

- **SOLID** principles
- **Clean Architecture** / Hexagonal Architecture
- **DRY** - Don't Repeat Yourself
- **Separation of Concerns**
- **Testability First**

## Workflows

SonarQube:

[![Quality Gate Status](http://104.219.236.212:9001/api/project_badges/measure?project=default-nerdlab-microservice&metric=alert_status&token=sqb_4736473f07b33e9fc1c3a00423ed4aa2ed92f5f2)](http://104.219.236.212:9001/dashboard?id=default-nerdlab-microservice)
