# Getting Started

## Prerequisites

- **Java 17** or newer (full JDK, not a JRE)
- **Git** command line tool
- An IDE of your choice (optional):
  - IntelliJ IDEA
  - Eclipse / Spring Tools Suite (STS)
  - VS Code

## Running with Maven

```bash
git clone https://github.com/awatson88/spring-petclinic.git
cd spring-petclinic
./mvnw spring-boot:run
```

## Running with Gradle

```bash
git clone https://github.com/awatson88/spring-petclinic.git
cd spring-petclinic
./gradlew bootRun
```

## Building a JAR

```bash
./mvnw package -DskipTests
java -jar target/spring-petclinic-4.0.0-SNAPSHOT.jar
```

The application will start on **http://localhost:8080/**.

## Running in an IDE

### IntelliJ IDEA

1. Open the project via `File -> Open` and select the `pom.xml`
2. A run configuration named `PetClinicApplication` should be auto-created
3. Otherwise, right-click on `PetClinicApplication` and choose `Run`

### Eclipse / STS

1. Import via `File -> Import -> Maven -> Existing Maven project`
2. Select the root directory of the cloned repo
3. Run `./mvnw generate-resources` to generate CSS
4. Right-click on `PetClinicApplication` and choose `Run As -> Java Application`

## Building a Container Image

There is no `Dockerfile` in this project. You can build a container image using the Spring Boot build plugin (requires a Docker daemon):

```bash
./mvnw spring-boot:build-image
```

## Compiling the CSS

The CSS is pre-compiled in the repo at `src/main/resources/static/resources/css/petclinic.css`. If you modify the SCSS source (`src/main/scss/petclinic.scss`) or upgrade Bootstrap, recompile using:

```bash
./mvnw package -P css
```

> Note: There is no Gradle profile for CSS compilation.
