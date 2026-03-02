# Controllers and Endpoints

## Overview

The application has four main controllers plus a welcome/error controller:

| Controller | Base Path | Purpose |
|-----------|-----------|---------|
| `WelcomeController` | `/` | Home page |
| `OwnerController` | `/owners` | Owner CRUD operations |
| `PetController` | `/owners/{ownerId}` | Pet CRUD (nested under owner) |
| `VisitController` | `/owners/{ownerId}/pets/{petId}` | Visit creation (nested under pet) |
| `VetController` | `/vets` | Vet listing |
| `CrashController` | `/oups` | Error page demo |

## Endpoints

### WelcomeController

| Method | Path | Description | View |
|--------|------|-------------|------|
| GET | `/` | Home page | `welcome.html` |

### OwnerController

| Method | Path | Description | View |
|--------|------|-------------|------|
| GET | `/owners/find` | Show find owner form | `owners/findOwners` |
| GET | `/owners?page=&lastName=` | Search owners by last name (paginated, 5 per page) | `owners/ownersList` |
| GET | `/owners/new` | Show create owner form | `owners/createOrUpdateOwnerForm` |
| POST | `/owners/new` | Create a new owner | Redirects to `/owners/{id}` |
| GET | `/owners/{ownerId}` | Show owner details | `owners/ownerDetails` |
| GET | `/owners/{ownerId}/edit` | Show edit owner form | `owners/createOrUpdateOwnerForm` |
| POST | `/owners/{ownerId}/edit` | Update owner | Redirects to `/owners/{ownerId}` |

**Notes:**
- The `findOwner` `@ModelAttribute` method automatically loads the owner from the database when `{ownerId}` is present in the URL
- Search supports partial last name matching (starts-with)
- If exactly one owner matches a search, it redirects directly to that owner's detail page

### PetController

All paths are prefixed with `/owners/{ownerId}`.

| Method | Path | Description | View |
|--------|------|-------------|------|
| GET | `/pets/new` | Show add pet form | `pets/createOrUpdatePetForm` |
| POST | `/pets/new` | Add a new pet to the owner | Redirects to `/owners/{ownerId}` |
| GET | `/pets/{petId}/edit` | Show edit pet form | `pets/createOrUpdatePetForm` |
| POST | `/pets/{petId}/edit` | Update pet details | Redirects to `/owners/{ownerId}` |

**Validation rules:**
- Pet names must be unique per owner
- Birth date cannot be in the future
- Pet type is required
- Custom `PetValidator` handles validation logic

### VisitController

| Method | Path | Description | View |
|--------|------|-------------|------|
| GET | `/owners/{ownerId}/pets/{petId}/visits/new` | Show new visit form | `pets/createOrUpdateVisitForm` |
| POST | `/owners/{ownerId}/pets/{petId}/visits/new` | Create a new visit | Redirects to `/owners/{ownerId}` |

**Notes:**
- Visit date defaults to the current date
- Description is required (`@NotBlank`)

### VetController

| Method | Path | Description | View / Response |
|--------|------|-------------|----------------|
| GET | `/vets.html?page=` | Show vet list (paginated, 5 per page) | `vets/vetList` |
| GET | `/vets` | REST endpoint returning all vets as JSON/XML | `Vets` object (`@ResponseBody`) |

### CrashController

| Method | Path | Description |
|--------|------|-------------|
| GET | `/oups` | Throws a `RuntimeException` to demonstrate error handling |

## Actuator Endpoints

Spring Boot Actuator is enabled with all endpoints exposed at `/actuator`:

| Path | Description |
|------|-------------|
| `/actuator` | List all available actuator endpoints |
| `/actuator/health` | Application health status |
| `/actuator/info` | Application info (build info, git commit) |
| `/actuator/metrics` | Application metrics |
| `/actuator/env` | Environment properties |
| `/actuator/beans` | All Spring beans |
| `/actuator/caches` | Cache statistics |
| ... | And more |
