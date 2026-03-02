# Internationalization (i18n)

## Overview

The application supports multiple languages using Spring's internationalization features. Users can switch languages via a URL parameter.

## Configuration

Internationalization is configured in `WebConfiguration.java`:

- **Locale Resolver**: `SessionLocaleResolver` stores the user's language preference in the HTTP session. Defaults to English.
- **Locale Change Interceptor**: Listens for a `lang` URL parameter to switch languages.

## Switching Languages

Append `?lang=<code>` to any URL to change the language:

```
http://localhost:8080/?lang=de     # German
http://localhost:8080/?lang=es     # Spanish
http://localhost:8080/?lang=fr     # French
http://localhost:8080/?lang=en     # English (default)
```

The language preference persists for the duration of the HTTP session.

## Message Files

Message bundles are located in `src/main/resources/messages/`:

| File | Language |
|------|----------|
| `messages.properties` | Default (English) |
| `messages_de.properties` | German |
| `messages_es.properties` | Spanish |
| `messages_fr.properties` | French |
| ... | Additional languages as available |

The base name is configured in `application.properties`:

```properties
spring.messages.basename=messages/messages
```

## Adding a New Language

1. Create a new properties file: `src/main/resources/messages/messages_<code>.properties`
2. Copy the keys from `messages.properties` and translate the values
3. The new language will be automatically available via `?lang=<code>`
