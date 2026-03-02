# Building and Deployment

## Build Tools

The project supports both **Maven** and **Gradle**.

## Maven Build

### Build JAR (skip tests)

```bash
./mvnw package -DskipTests
```

### Build JAR (with tests)

```bash
./mvnw package
```

### Run directly

```bash
./mvnw spring-boot:run
```

### Build with CSS compilation

```bash
./mvnw package -P css
```

## Gradle Build

### Build JAR

```bash
./gradlew build
```

### Run directly

```bash
./gradlew bootRun
```

## Container Image

Build a container image using the Spring Boot Maven plugin (requires a Docker daemon):

```bash
./mvnw spring-boot:build-image
```

## Docker Compose

A `docker-compose.yml` is provided for running database containers:

```bash
# Start MySQL
docker compose up mysql

# Start PostgreSQL
docker compose up postgres
```

## Kubernetes

Kubernetes deployment manifests are provided in the `k8s/` directory.

## GraalVM Native Image

The project includes GraalVM native image support via the `native-maven-plugin`. Runtime hints are provided by `PetClinicRuntimeHints.java`.

To build a native image:

```bash
./mvnw -Pnative native:compile
```

> Note: Requires GraalVM JDK installed.

## Build Plugins

| Plugin | Purpose |
|--------|---------|
| `spring-boot-maven-plugin` | Executable JAR packaging, build-info generation |
| `maven-enforcer-plugin` | Enforces Java 17+ |
| `spring-javaformat-maven-plugin` | Code style validation |
| `maven-checkstyle-plugin` | Code quality checks (includes nohttp) |
| `jacoco-maven-plugin` | Code coverage |
| `git-commit-id-maven-plugin` | Git info for Actuator |
| `cyclonedx-maven-plugin` | SBOM generation |
| `native-maven-plugin` | GraalVM native image support |

## Actuator Build Info

The application exposes build information via Spring Boot Actuator at `/actuator/info`, including:
- Source encoding
- Java version
- Git commit info
- SBOM (Software Bill of Materials)
