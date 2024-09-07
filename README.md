# SpringCleanArchitectureCQRS

This project demonstrates the implementation of Clean Architecture and CQRS (Command Query Responsibility Segregation) using Spring Boot and Maven. It focuses on maintaining separation of concerns, scalability, and testability while adhering to modern software design principles.

## Project Structure

The project follows Clean Architecture principles and is organized into several layers:

- **Domain Layer**:  
  This is the core layer containing business logic and domain entities. It is independent of any external framework, ensuring high testability and flexibility. Key components include:
    - **Entities**: Core business entities like `Order`, `Product`, `User`, `Payment`, and `Notification`.
    - **Domain Services**: Business logic that cannot fit into a single entity.
    - **Enums**: Custom enums like `OrderStatus`, `ProductStatus`, `NotificationStatus` that define key states in the system.

- **Application Layer**:  
  Defines the interaction between the Domain layer and the outside world. This layer includes:
    - **Use Cases**: Command and Query UseCases, implementing CQRS pattern to handle business logic operations such as creating, updating, and deleting orders.
    - **Services**: Business services for each use case.
    - **Exception Handling**: Custom exceptions like `OrderNotFoundException`, `ProductNotFoundException`, and a global exception handler for graceful error handling.
    - **Payment Use Cases**:
        - `PaymentCreateUseCase` handles the creation of payments and manages insufficient funds scenarios by canceling the order if necessary.
        - `PaymentFindByIdUseCase` ensures that payment records exist before proceeding with other operations like notifications.
    - **Notification Use Cases**:
        - `NotificationCreateUseCase` handles the creation of notifications based on events like successful or failed payments.
    - **DTOs**: Data Transfer Objects (DTOs) used to transfer data between layers.
    - **Mappers**: Mapping between entities and DTOs.

- **Infrastructure Layer**:  
  Responsible for external services and technologies like databases, messaging, and APIs. This includes:
    - **Kafka Configuration**: Includes Kafka configuration for producing and consuming events related to Orders, Payments, and Notifications.
    - **Docker Configuration**: Includes Docker configuration for running Kafka, Zookeeper, and PostgreSQL in containers.

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

- **Event-Driven Architecture**:
  Uses Kafka for event-driven communication between services, enabling asynchronous processing and decoupling of services.

- **Kafka Retry and Callback Mechanism**:  
  Implements Kafka callback and retry mechanisms to ensure messages are sent reliably and handle errors gracefully. This includes retrying failed messages and acknowledging successful ones.

- **Payment Validation**:
  The system ensures that any payment-related operation verifies the existence of a payment record before proceeding. This prevents issues where messages might be processed out of order, causing foreign key constraint violations.

- **Spring Boot**:  
  Leverages Spring Boot for dependency injection, REST API development, and integration with other enterprise features like validation and security.

- **Maven**:  
  The project is built and managed using Maven, ensuring easy dependency management, build automation, and continuous integration.

## Kafka Integration

The project uses Kafka to enable communication between the order, payment, and notification services. Kafka is used to decouple the services, allowing them to scale independently and ensuring loose coupling in the architecture.

### Order Creation Flow with Kafka

1. **Order Creation**:  
   When an order is created, an `OrderEvent` is published to a Kafka topic named `order-events`.

2. **Payment Processing**:  
   A Kafka listener (`HandleOrderMessage`) listens for `OrderEvent`s. It processes payments based on the order amount and the user's balance. If the balance is insufficient, the order status is updated to `CANCELLED`.

3. **Payment Existence Validation**:  
   Before processing any payment-related message, the system ensures that the payment exists in the database using the `PaymentFindByIdUseCase`. This prevents errors like processing notifications for payments that haven't been created yet.

4. **Notification Handling**:  
   A Kafka listener (`HandlePaymentNotificationMessage`) listens for `PaymentEvent`s. It checks if the payment exists before creating notifications with appropriate statuses based on the payment outcome.

### Example Kafka Topics:

- `order-events`: Used to publish order creation events.
- `order-update-events`: Used to publish order status updates.
- `payment-create`: Used to publish payment creation events.
- `notification-events`: Used to handle notifications based on payment status.

### Configuration:

```yaml
# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=payment-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.trusted.packages=*

spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer

# Kafka Topic Names
spring.kafka.topic.order=order-events
spring.kafka.topic.order-update=order-update-events
spring.kafka.topic.payment-create=payment-create
spring.kafka.topic.notification-events=notification-events
```

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

- **Create User**:  
  `POST /api/v1/users/command`  
  Example Request Body:
  ```json
    {
    "name": "Emir SoGood",
    "email": "sogutemir72@gmail.com",
    "money": 50000.00
    }   
  ```
  
- **Create Order**:  
  `POST /api/v1/orders/command`  
  Example Request Body:
  ```json
  {
    "userId": 1,
    "totalAmount": 150.00,
    "productQuantities": {
        "1": 5,
        "2": 5,
        "3": 5
    }
  }
  ```

- **Update Order**:  
  `PUT /api/v1/orders/command/{id}`  
  Example Request Body:
  ```json
  {
    "userId": 3,
    "totalAmount": 126.00,
    "productQuantities": {
    "1": 5,
    "2": 5,
    "3": 8
    }
  }
  ```

### Some Query Endpoints (Read Operations)

- **Get Order by ID**:  
  `GET /api/v1/orders/query/{id}`
- **Get Notifications By User ID**:  
  `GET /api/v1/notifications/query/user/{userId}`

## Unit Testing

The project includes comprehensive unit tests for controllers, services, and use cases. Test coverage includes edge cases, exception handling, and business logic validation.

### How to run the tests:

To run all the unit tests:
```bash
mvn test
```

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
