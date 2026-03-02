# Architecture Overview

## Technology Stack

| Layer | Technology |
|-------|-----------|
| Framework | Spring Boot 4.0.1 |
| Web | Spring MVC (WebMVC) |
| Templates | Thymeleaf |
| Data Access | Spring Data JPA + Hibernate 7.2.0 |
| Database (default) | H2 (in-memory) |
| Database (production) | MySQL or PostgreSQL |
| Validation | Jakarta Bean Validation |
| Caching | JCache API with Caffeine |
| Build | Maven 3.9+ / Gradle |
| Java | 17+ |
| CSS Framework | Bootstrap 5.3.8 |

## Package Structure

```
org.springframework.samples.petclinic
├── PetClinicApplication.java          # Main Spring Boot application class
├── PetClinicRuntimeHints.java         # GraalVM native image hints
├── model/                             # Base domain model classes
│   ├── BaseEntity.java                # Base entity with ID
│   ├── NamedEntity.java               # Entity with name property
│   ├── Person.java                    # Entity with first/last name
│   └── package-info.java
├── owner/                             # Owner, Pet, Visit domain + controllers
│   ├── Owner.java                     # Owner entity
│   ├── OwnerController.java           # Owner CRUD endpoints
│   ├── OwnerRepository.java           # Owner data access
│   ├── Pet.java                       # Pet entity
│   ├── PetController.java             # Pet CRUD endpoints
│   ├── PetType.java                   # Pet type entity (cat, dog, etc.)
│   ├── PetTypeFormatter.java          # Formatter for PetType
│   ├── PetTypeRepository.java         # PetType data access
│   ├── PetValidator.java              # Custom pet validation
│   ├── Visit.java                     # Visit entity
│   ├── VisitController.java           # Visit creation endpoints
│   └── package-info.java
├── system/                            # System-level configuration
│   ├── CacheConfiguration.java        # JCache/Caffeine caching setup
│   ├── CrashController.java           # Error page demonstration
│   ├── WebConfiguration.java          # i18n configuration
│   ├── WelcomeController.java         # Home page controller
│   └── package-info.java
└── vet/                               # Veterinarian domain + controllers
    ├── Specialty.java                 # Vet specialty entity
    ├── Vet.java                       # Vet entity
    ├── VetController.java             # Vet listing endpoints
    ├── VetRepository.java             # Vet data access
    ├── Vets.java                      # Wrapper for XML/JSON serialization
    └── package-info.java
```

## Layered Architecture

The application follows a classic **3-layer architecture**:

### 1. Presentation Layer (Controllers)
- Spring MVC `@Controller` classes handle HTTP requests
- Thymeleaf templates render HTML views
- Controllers use `@ModelAttribute` for request-scoped model population
- Form validation via `@Valid` and `BindingResult`

### 2. Service/Repository Layer (Data Access)
- Spring Data JPA repositories provide CRUD operations
- `JpaRepository` interfaces with derived query methods
- No explicit service layer -- controllers interact directly with repositories

### 3. Domain Layer (Entities)
- JPA entities mapped to database tables
- Inheritance hierarchy: `BaseEntity` -> `NamedEntity` / `Person`
- Relationships managed via JPA annotations (`@OneToMany`, `@ManyToMany`, etc.)

## Configuration

The application uses Spring Boot auto-configuration with properties defined in:
- `application.properties` -- default (H2) configuration
- `application-mysql.properties` -- MySQL profile
- `application-postgres.properties` -- PostgreSQL profile

Key configuration features:
- **Thymeleaf** in HTML mode
- **JPA** with `ddl-auto=none` (schema managed by SQL scripts)
- **Actuator** with all endpoints exposed
- **Static resource caching** set to 12 hours
