![Overview Image](/pngegg.png)
# 👨🏻‍💻 TUI DX Backend Technical Test v2

1. [Overview](#1-overview)
2. [Conditions](#2-conditions)
3. [Tech Stack](#3-tech-stack)
4. [Prerequisites](#4-prerequisites)
5. [Operations](#5-operations)
6. [API Documentation](#6-api-documentation)
7. [Database](#7-database)
8. [Security](#8-security)
9. [Getting Started](#9-getting-started)
10. [Running Tests](#10-running-tests)
11. [Postman Collection](#11-postman-collection)
12. [cURL](#12-curl)

## 1. Overview

Welcome to the TUI DX Backend Technical Test v2. This project is designed to evaluate your skills in backend development using Java, Spring Boot, and related technologies.

## 2. Conditions

- Create a pilotes order (5, 10, or 15 pilotes).
- Update a pilotes order within 5 minutes of creation.
- Search orders by customer name with partial matching.
- Data validation.
- Secured search operation, other operations are public.

## 3. Tech Stack

- **Java**: Core programming language.
- **Spring Boot**: Framework for building microservices.
- **Spring Data JPA**: Data access framework.
- **Spring Security**: Authentication and authorization framework.
- **H2**: In-memory database.
- **Gradle**: Build automation tool.
- **Lombok**: Reduces boilerplate code.
- **Swagger**: API documentation.
- **Spock**: Testing and specification framework.
- **Jacoco**: Code coverage tool.
- **Checkstyle**: Code quality tool.
- **Veracode**: Security scanning tool.
- **SonarLint**: Static code analysis tool.
- **Docker**: Containerization tool.

## 4. Prerequisites

Ensure you have the following installed:

- Java 11 or higher
- Gradle 6.9.4 or higher
- Lombok (Follow the [installation guide](https://www.baeldung.com/lombok-ide))
- Docker 

## 5. Operations

Details of the operations are as follows:

| Operation      | Method | Description                   |
|----------------|--------|-------------------------------| 
| /orders        | POST   | Create a pilotes order        |
| /orders/{id}   | PUT    | Update a pilotes order        |
| /orders/search | GET    | Search orders by customer info |

## 6. API Documentation

The API documentation is available at
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).
- OpenAPI Docs: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs).

## 7. Database

The H2 database console is available at [http://localhost:8080/h2-console](http://localhost:8080/h2-console).
- jdbc url: jdbc:h2:mem:testdb
- username: admin
- password: admin

## 8. Security

- Search Operation: Secured, requires authentication (demo user - user/user).
- Create and Update Operations: Public, no authentication required.

## 9. Getting Started 🚀

1. **Clone the repository:**
    ```sh
    git clone https://github.com/aandmaldonado/backend-technical-test-v2.git
    cd backend-technical-test-v2
    ```

2. **Build the project:**
    ```sh
    ./gradlew build
    ```

3. **Run the application:**
    ```sh
    ./gradlew bootRun
    ```

## 10. Running Tests

To execute the tests, run:
```sh
./gradlew test
```
To check code coverage, run:
```sh
./gradlew jacocoTestReport
```
The coverage report will be generated in `build/reports/jacoco/test/html/index.html`.

## 11. Postman Collection

You can find the Postman collection to test the APIs [here](backend-technical-test-v2.postman_collection.json).

## 12. cURL

1. **Create Order:**
```sh
curl --location 'localhost:8080/orders' \
--header 'Content-Type: application/json' \
--data '{
    "client": {
        "firstName": "Álvaro",
        "lastName": "Maldonado",
        "telephone": "+34641962396"
    },
    "deliveryAddress": {
        "city": "Gandia",
        "street": "Carrer de França 12",
        "postcode": "46730",
        "country": "Spain"
    },
    "pilotes": "5"
}'
```
2. **Update Order:**
```sh
curl --location --request PUT 'localhost:8080/orders/1' \
--header 'Content-Type: application/json' \
--data '{
    "client": {
        "firstName": "Álvaro",
        "lastName": "Maldonado",
        "telephone": "+34641962396"
    },
    "deliveryAddress": {
        "city": "Gandia",
        "street": "Carrer de França 12",
        "postcode": "46730",
        "country": "Spain"
    },
    "pilotes": "5"
}'
```
3. **Search Order:**
```sh
curl --location 'localhost:8080/orders/search?filter=%C3%81lva' \
--header 'Authorization: Basic dXNlcjp1c2Vy' \
--header 'Cookie: JSESSIONID=8858AD0BFADC46B74E237F686474909C' \
--data ''
```