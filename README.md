# SpringCleanArchitectureCQRS

This project demonstrates the implementation of Clean Architecture and CQRS (Command Query Responsibility Segregation) using Spring Boot and Maven. It focuses on maintaining separation of concerns, scalability, and testability while adhering to modern software design principles.

## Project Structure

The project follows Clean Architecture principles and is organized into several layers:

- **Domain Layer**:  
  This is the core layer containing business logic and domain entities. It is independent of any external framework, ensuring high testability and flexibility. Key components include:
    - **Entities**: Core business entities like `Order`, `Product`, `User`, etc.
    - **Value Objects**: Immutable objects encapsulating domain-specific rules.
    - **Domain Services**: Business logic that cannot fit into a single entity.
    - **Enums**: Custom enums like `OrderStatus`, `ProductStatus` that define key states in the system.

- **Application Layer**:  
  Defines the interaction between the Domain layer and the outside world. This layer includes:
    - **Use Cases**: Command and Query UseCases, implementing CQRS pattern to handle business logic operations such as creating, updating, and deleting orders.
    - **Services**: Business services for each use case.
    - **DTOs**: Data Transfer Objects (DTOs) used to transfer data between layers.
    - **Exception Handling**: Custom exceptions like `OrderNotFoundException`, `ProductNotFoundException`, and a global exception handler for graceful error handling.

- **Infrastructure Layer**:  
  Responsible for external services and technologies like databases, messaging, and APIs. This includes:
    - **Repositories**: Interfaces for data persistence, separated into Command and Query repositories to align with CQRS principles.
    - **Adapters**: Persistence adapters for interaction with external storage.
    - **Configuration**: Includes database, Kafka, and other external configurations.

- **Presentation Layer**:  
  Exposes APIs to interact with the application, using REST controllers for the CQRS-based architecture.
    - **Command Controllers**: For handling Create, Update, and Delete operations.
    - **Query Controllers**: For handling read (GET) operations.

## Key Features

- **Clean Architecture**:  
  Decouples the business logic from frameworks and external systems. The architecture enforces clear separation between layers, enabling flexible and scalable development.

- **CQRS (Command Query Responsibility Segregation)**:  
  Implements CQRS to separate the handling of commands (write operations) and queries (read operations). This pattern improves scalability and maintainability, making the system more modular and easier to extend.

- **Domain-Driven Design (DDD)**:  
  Follows DDD principles to encapsulate business logic within domain models, ensuring that domain behavior is consistent and follows business rules.

- **Spring Boot**:  
  Leverages Spring Boot for dependency injection, REST API development, and integration with other enterprise features like validation and security.

- **Kafka Integration**:  
  Uses Kafka for event-driven communication between services, supporting the scalability and decoupling of the system.

- **Maven**:  
  The project is built and managed using Maven, ensuring easy dependency management, build automation, and continuous integration.

## Exception Handling

The project features centralized exception handling through a global exception handler using Spring's `@RestControllerAdvice`. The following example shows handling of a custom exception:

## How to Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/sogutemir/SpringCleanArchitectureCQRS.git
   ```

2. **Navigate to the project directory**:
   ```bash
   cd SpringCleanArchitectureCQRS
   ```

3. **Build the project using Maven**:
   ```bash
   mvn clean install
   ```

4. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

### Command Endpoints (Write Operations)

- **Create Order**:  
  `POST /api/v1/orders/command`  
  Example Request Body:
  ```json
  {
    "userId": 1,
    "productIds": [1, 2],
    "totalAmount": 350
  }
  ```

- **Update Order**:  
  `PUT /api/v1/orders/command/{id}`  
  Example Request Body:
  ```json
  {
    "userId": 1,
    "productIds": [1, 2],
    "totalAmount": 400
  }
  ```

- **Delete Order**:  
  `DELETE /api/v1/orders/command/{id}`

### Query Endpoints (Read Operations)

- **Get Order by ID**:  
  `GET /api/v1/orders/query/{id}`

## Unit Testing

The project includes comprehensive unit tests for controllers, services, and use cases. Test coverage includes edge cases, exception handling, and business logic validation.

### How to run the tests:

To run all the unit tests:
```bash
mvn test
```

Unit tests are located in the `src/test/java` directory. The tests cover:

- **Order Creation**: Verifying that an order is correctly created with valid inputs.
- **Order Update**: Testing order updates with various scenarios including product stock validation.
- **Order Deletion**: Ensuring that orders are correctly deleted and handling cases where orders are not found.


## Technologies Used

- **Java**: Core programming language for the project.
- **Spring Boot**: Framework for building the application.
- **Spring Data JPA**: For data persistence and interaction with the database.
- **Kafka**: For event-driven communication between services.
- **Maven**: Build automation and dependency management.
- **H2 Database**: In-memory database used for testing.

## License

This project is licensed under the MIT License.

---
