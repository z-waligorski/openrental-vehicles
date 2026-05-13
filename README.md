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
- Docker support
- Lombok-based boilerplate reduction

## Tech Stack

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Jakarta Validation
- PostgreSQL
- Flyway
- Lombok
- Maven
- Docker

## Requirements

Before running the project, make sure you have installed:

- Java 21+
- Maven 3.9+ or use the included Maven Wrapper
- Docker and Docker Compose 
- PostgreSQL, if running without Docker 
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

Available Spring profiles:

| Profile | Description |
|---|---|
| `dev` | Local development configuration |
| `docker` | Docker Compose configuration |


## Running with Docker

### 1. Build the application

```bash
./mvnw clean package
```

### 2. Build the Docker image

```bash
docker build -t openrental-vehicles:1.0 .
```

### 3. Start services

```bash
docker compose up
```

The application will be available at:

```text
http://localhost:8080
```

PostgreSQL is exposed on the host at:

```text
localhost:5435
```
Deployment is possible also with kubernetes, as described in the openrental-infra project.

## API Endpoints

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
curl "http://localhost:8080/cars?page=0&size=10"
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


## License

This project is intended for learning and practice purposes.