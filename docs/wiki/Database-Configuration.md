# Database Configuration

## Default: H2 (In-Memory)

By default, PetClinic uses an **H2 in-memory database** that is populated at startup from SQL scripts. No external database is needed.

- **H2 Console**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:<uuid>` (the UUID is printed in the console at startup)
- **Username**: `SA`
- **Password**: (empty)

The database schema and seed data are loaded from:
- Schema: `src/main/resources/db/h2/schema.sql`
- Data: `src/main/resources/db/h2/data.sql`

## MySQL

To use MySQL, activate the `mysql` Spring profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql
```

### Start MySQL with Docker

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.5
```

Or using Docker Compose:

```bash
docker compose up mysql
```

### MySQL Configuration

Defined in `application-mysql.properties`:

| Property | Value |
|----------|-------|
| URL | `jdbc:mysql://localhost/petclinic` |
| Username | `petclinic` |
| Password | `petclinic` |
| Driver | MySQL Connector/J |

## PostgreSQL

To use PostgreSQL, activate the `postgres` Spring profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
```

### Start PostgreSQL with Docker

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:18.1
```

Or using Docker Compose:

```bash
docker compose up postgres
```

### PostgreSQL Configuration

Defined in `application-postgres.properties`:

| Property | Value |
|----------|-------|
| URL | `jdbc:postgresql://localhost/petclinic` |
| Username | `petclinic` |
| Password | `petclinic` |
| Driver | PostgreSQL JDBC Driver |

## Schema Management

- JPA `ddl-auto` is set to `none` -- Hibernate does **not** auto-generate the schema
- Schema is managed via SQL scripts located in `src/main/resources/db/{database}/`
- Each database type (h2, mysql, postgres) has its own `schema.sql` and `data.sql`

## Sample Data

The application ships with sample data including:
- **10 owners** with pets
- **6 veterinarians** with specialties
- **6 pet types**: cat, dog, lizard, snake, bird, hamster
- **3 specialties**: radiology, surgery, dentistry
