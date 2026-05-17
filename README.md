# OpenRental Vehicles

OpenRental Vehicles is a Spring Boot service for managing vehicles in a rental system.  
It provides REST APIs for creating, reading, updating, deleting, listing, and filtering vehicle data.

The project is built as a practice application for Spring Boot and related backend technologies.

## Features

- Manage cars
- Manage motorcycles
- REST API endpoints
- Pagination support
- Car filtering endpoint
- PostgreSQL database integration
- Flyway database migrations
- Bean validation
- Keycloak-based authentication and authorization
- Role-based access control
- Docker support
- Lombok-based boilerplate reduction

## Tech Stack

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- OAuth2 Resource Server
- Jakarta Validation
- PostgreSQL
- Flyway
- Keycloak
- Lombok
- Maven
- Docker

## Requirements

Before running the project, make sure you have installed:

- Java 21+
- Maven 3.9+ or use the included Maven Wrapper
- Docker and Docker Compose 
- PostgreSQL, if running without Docker 
- Keycloak, if running authentication services outside Docker
- Alternatively, the project can be deployed with Kubernetes

## Project Structure

```text
openrental-vehicles
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.eprogram.openrental_vehicles
│   │   └── resources
│   │       ├── application.yaml
│   │       ├── application-dev.yaml
│   │       ├── application-docker.yaml
│   │       └── db
│   └── test
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```


## Configuration

The application uses environment variables for the default datasource configuration:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

The application also uses Keycloak for authentication and authorization.  
Configure the OAuth2 issuer URI for the Keycloak realm:

```text
KEYCLOAK_ISSUER_URI
KEYCLOAK_JWKS_URI
```

Available Spring profiles:

| Profile | Description |
|---|---|
| `dev` | Local development configuration |
| `docker` | Docker Compose configuration |

## Security

The service is protected with Spring Security and Keycloak.

Clients must authenticate with Keycloak and send a valid JWT access token when calling protected endpoints.

Example request:
```bash
bash curl -H "Authorization: Bearer <access-token>"
http://localhost:8080/cars
```


Access to endpoints is controlled using roles configured in Keycloak.

Typical roles:

| Role | Description |
|---|---|
| `admin` | Full access to protected vehicle management operations |
| `user` | Limited access to read-only or user-level operations |

The exact permissions depend on the security configuration in the application.

## Running with Docker

### Start services
Make sure that environmental variable is set for POSTGRES_PASSWORD and Keycloak-related values.

Start the application:

```bash
docker compose up --build
```

The application will be available at:

```text
http://localhost:8080
```

PostgreSQL is exposed on the host at:

```text
localhost:5435
```

Keycloak must be running separately and reachable from the application using the configured issuer URI.

Deployment is possible also with kubernetes, as described in the openrental-infra project.

## API Endpoints

API endpoints require authentication using a valid Keycloak JWT access token.

Include the token in the `Authorization` header:
```text
Authorization: Bearer <access-token>
```

### Cars

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/cars` | Get paginated list of cars |
| `GET` | `/cars/{id}` | Get car by ID |
| `POST` | `/cars` | Create a new car |
| `PUT` | `/cars/{id}` | Update an existing car |
| `DELETE` | `/cars/{id}` | Delete car by ID |
| `GET` | `/cars/filter` | Filter cars |


### Motorcycles

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/motorcycles` | Get paginated list of motorcycles |
| `GET` | `/motorcycles/{id}` | Get motorcycle by ID |
| `POST` | `/motorcycles` | Create a new motorcycle |
| `PUT` | `/motorcycles/{id}` | Update an existing motorcycle |
| `DELETE` | `/motorcycles/{id}` | Delete motorcycle by ID |



## Pagination

List endpoints support Spring Data pagination parameters:

```text
?page=0&size=10&sort=brand,asc
```

Example:

```bash
curl -H "Authorization: Bearer <access-token>
"http://localhost:8080/cars?page=0&size=10"
```


## Database Migrations

Database schema changes are managed with Flyway.

Migration files should be placed in:

```text
src/main/resources/db/migration
```

Flyway runs automatically when the application starts.


## Building the Project

```bash
./mvnw clean package
```

The generated JAR file will be located in:

```text
target/
```

## Planned Improvements
- Further features for motorcycles
- Integration tests for secured endpoints
- Code improvements, refactoring, and optimization

## License

This project is intended for learning and practice purposes.