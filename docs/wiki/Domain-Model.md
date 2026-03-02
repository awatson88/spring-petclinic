# Domain Model

## Entity Hierarchy

```
BaseEntity (id)
├── NamedEntity (name)
│   ├── PetType          # e.g., cat, dog, lizard, snake, bird, hamster
│   ├── Specialty         # e.g., radiology, surgery, dentistry
│   └── Pet              # name, birthDate, type, visits
├── Person (firstName, lastName)
│   ├── Owner            # address, city, telephone, pets
│   └── Vet              # specialties
└── Visit                # date, description
```

## Entities

### BaseEntity
The root of the entity hierarchy. Provides an auto-generated `id` field using `@GeneratedValue(strategy = GenerationType.IDENTITY)`.

| Field | Type | Description |
|-------|------|-------------|
| `id` | `Integer` | Auto-generated primary key |

### NamedEntity
Extends `BaseEntity` with a `name` property. Used as a base for simple named reference entities.

| Field | Type | Constraints | Description |
|-------|------|------------|-------------|
| `name` | `String` | `@NotBlank` | Display name |

### Person
Extends `BaseEntity` with first and last name. Used as a base for people in the system.

| Field | Type | Constraints | Description |
|-------|------|------------|-------------|
| `firstName` | `String` | `@NotBlank` | First name |
| `lastName` | `String` | `@NotBlank` | Last name |

### Owner
Extends `Person`. Represents a pet owner with contact information and a list of pets.

| Field | Type | Constraints | Description |
|-------|------|------------|-------------|
| `address` | `String` | `@NotBlank` | Street address |
| `city` | `String` | `@NotBlank` | City |
| `telephone` | `String` | `@NotBlank`, `@Pattern("\\d{10}")` | 10-digit phone number |
| `pets` | `List<Pet>` | `@OneToMany(cascade=ALL, fetch=EAGER)` | Owned pets, ordered by name |

### Pet
Extends `NamedEntity`. Represents a pet with a type, birth date, and visit history.

| Field | Type | Description |
|-------|------|-------------|
| `birthDate` | `LocalDate` | Date of birth (format: `yyyy-MM-dd`) |
| `type` | `PetType` | `@ManyToOne` reference to pet type |
| `visits` | `Set<Visit>` | `@OneToMany(cascade=ALL, fetch=EAGER)`, ordered by date ASC |

### PetType
Extends `NamedEntity`. Simple reference entity for pet species.

### Visit
Extends `BaseEntity`. Records a vet visit for a pet.

| Field | Type | Constraints | Description |
|-------|------|------------|-------------|
| `date` | `LocalDate` | -- | Visit date (defaults to current date) |
| `description` | `String` | `@NotBlank` | Description of the visit |

### Vet
Extends `Person`. Represents a veterinarian with specialties.

| Field | Type | Description |
|-------|------|-------------|
| `specialties` | `Set<Specialty>` | `@ManyToMany(fetch=EAGER)` via join table `vet_specialties` |

### Specialty
Extends `NamedEntity`. Simple reference entity for vet specialties (e.g., radiology, surgery, dentistry).

## Database Tables

| Table | Entity |
|-------|--------|
| `owners` | Owner |
| `pets` | Pet |
| `types` | PetType |
| `visits` | Visit |
| `vets` | Vet |
| `specialties` | Specialty |
| `vet_specialties` | Vet-Specialty join table |

## Entity Relationship Diagram

```
┌──────────┐       ┌──────────┐       ┌──────────┐
│  Owner   │1    * │   Pet    │1    * │  Visit   │
│──────────│───────│──────────│───────│──────────│
│ firstName│       │ name     │       │ date     │
│ lastName │       │ birthDate│       │description│
│ address  │       │          │       └──────────┘
│ city     │       │          │
│ telephone│       │     type │
└──────────┘       └─────┬────┘
                         │ *..1
                   ┌─────┴────┐
                   │ PetType  │
                   │──────────│
                   │ name     │
                   └──────────┘

┌──────────┐       ┌───────────┐
│   Vet    │*    * │ Specialty │
│──────────│───────│───────────│
│ firstName│       │ name      │
│ lastName │       └───────────┘
└──────────┘
```
