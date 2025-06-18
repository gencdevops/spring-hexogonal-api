# Reservation API

This project is a reservation management API developed following **Hexagonal Architecture (Ports & Adapters)** and **Domain-Driven Design (DDD)** principles.

## 🏗️ Architecture Approach

### Hexagonal Architecture
The project adopts a clean architecture approach and inverts dependencies:
- **Domain**: Core layer containing business logic and rules
- **Application**: Layer where use cases are implemented
- **Infrastructure**: Communication layer with external world like database, HTTP, message queues

### Domain-Driven Design
Business logic focused design:
- **Aggregates**: Main domain objects encapsulating business rules
- **Value Objects**: Immutable value objects
- **Domain Services**: Services containing domain logic
- **Domain Events**: Events notifying domain changes

## 🔧 Technologies

- **Spring Boot 3.3.0** - Framework
- **Java 21** - Programming Language
- **Spring Data JPA** - Data Access
- **H2 Database** - In-Memory Database
- **MapStruct** - Object Mapping
- **OpenFeign** - HTTP Client
- **JUnit 5** - Testing Framework
- **Lombok** - Code Generation

## 🚀 Setup and Running

### Requirements
- Java 21
- Maven 3.8+

### Running the Project

```bash
# Compile the project
mvn clean compile

# Run tests
mvn test

# Run integration tests
mvn integration-test

# Start the application
mvn spring-boot:run
```

### H2 Database Console
Access H2 console after running the application:
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## 📖 API Documentation

Swagger UI: http://localhost:8080/

## 🧪 Testing Strategy

- **Unit Tests**: Domain and Application layer tests
- **Integration Tests**: Infrastructure layer tests
- **Test Coverage**: Code coverage reports with JaCoCo

```bash
# Generate test coverage report
mvn clean test jacoco:report
```

## 🔍 Monitoring

Actuator endpoints:
- Health: http://localhost:8080/_monitoring/health
- Metrics: http://localhost:8080/_monitoring/prometheus

## 📋 Development Principles

### Domain-Driven Design Rules
1. Domain layer should not have any external dependencies
2. Business logic should only exist in the Domain layer
3. Infrastructure layer should implement Domain interfaces
4. Use cases should only communicate with domain

### Hexagonal Architecture Rules
1. Dependencies should always point inward
2. Domain layer should be framework independent
3. Adapters should implement ports
4. Configuration should only be in Infrastructure layer

## 🏷️ Version

Current version: **0.0.1-SNAPSHOT**