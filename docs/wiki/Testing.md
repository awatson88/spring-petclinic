# Testing

## Overview

The project includes a comprehensive test suite covering unit tests, integration tests, and database-specific integration tests.

## Test Structure

```
src/test/java/org/springframework/samples/petclinic/
├── PetClinicIntegrationTests.java         # Main integration tests (H2)
├── MySqlIntegrationTests.java             # MySQL integration tests (Testcontainers)
├── MysqlTestApplication.java              # MySQL test app configuration
├── PostgresIntegrationTests.java          # PostgreSQL integration tests (Docker Compose)
├── model/
│   └── ValidatorTests.java                # Bean validation tests
├── owner/
│   ├── OwnerControllerTests.java          # Owner controller unit tests
│   ├── PetControllerTests.java            # Pet controller unit tests
│   ├── PetTypeFormatterTests.java         # PetType formatter tests
│   ├── PetValidatorTests.java             # Pet validator tests
│   └── VisitControllerTests.java          # Visit controller unit tests
├── service/
│   ├── ClinicServiceTests.java            # Service-level integration tests
│   └── EntityUtils.java                   # Test utilities
├── system/
│   ├── CrashControllerIntegrationTests.java  # Error handling integration tests
│   ├── CrashControllerTests.java             # Error controller unit tests
│   └── I18nPropertiesSyncTest.java           # i18n property sync validation
└── vet/
    ├── VetControllerTests.java            # Vet controller unit tests
    └── VetTests.java                      # Vet domain tests
```

## Running Tests

### All Tests (H2 only)

```bash
./mvnw test
```

### With MySQL (requires Docker)

MySQL integration tests use **Testcontainers** to automatically spin up a MySQL container:

```bash
./mvnw test -Dspring.profiles.active=mysql
```

### With PostgreSQL (requires Docker)

PostgreSQL integration tests use **Docker Compose**:

```bash
./mvnw test -Dspring.profiles.active=postgres
```

## Test Applications

For development, you can run test applications directly from your IDE:

| Class | Database | Features |
|-------|----------|----------|
| `PetClinicIntegrationTests` | H2 (default) | Includes Spring Boot DevTools |
| `MysqlTestApplication` | MySQL (Testcontainers) | Auto-starts MySQL in Docker |
| `PostgresIntegrationTests` | PostgreSQL (Docker Compose) | Auto-starts PostgreSQL via Docker Compose |

## Testing Technologies

| Technology | Purpose |
|-----------|---------|
| JUnit 5 | Test framework |
| Spring Boot Test | Integration testing support |
| MockMvc | Web layer testing without server |
| Testcontainers | MySQL container management |
| Docker Compose | PostgreSQL container management |
| JaCoCo | Code coverage reporting |
