# User Service

## Overview

The User Service is a core component of the E-Commerce Application responsible for managing user-related functionalities, including authentication and authorization. It utilizes OAuth 2 for secure authentication and authorization processes.

## Features

- **User Management**: Handles user signup, login, and profile management.
- **Token Management**: Includes services for token validation and logout.
- **OAuth 2 Implementation**: Provides secure authentication and authorization using OAuth 2.
- **User Details**: Allows retrieval of user details.

## Technologies Used

- **Java**: Programming language used for implementation.
- **Spring Boot**: Framework for building the application.
- **Spring Data JPA**: For database interaction.
- **Spring Security**: Provides authentication and authorization features.
- **Spring Boot OAuth2 Authorization Server**: Manages OAuth 2 authorization.
- **MySQL**: Database for storing user information.
- **Spring Cloud Netflix Eureka**: For service registration and discovery.
- **Spring Kafka**: For messaging between services.
- **Lombok**: Reduces boilerplate code.
- **Apache Commons Lang**: Provides additional functionalities for string and date manipulation.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- MySQL

### Steps to Run

1. **Clone the Repository**  
   Clone this repository to your local machine:
   ```bash
   git clone [https://github.com/AmanPr01/UserService](https://github.com/AmanPr01/UserService)
   cd E-Commerce/user-service
   ```
2. **Configure Database Settings**
   Update the src/main/resources/application.properties file with your MySQL database configuration:
   ```bash
   properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build the Application**
   Use Maven to build the application:
   ```bash
   mvn clean install
   ```
   
4. **Run the Application**
   Start the application using Maven:
   
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**
   The API will be accessible at http://localhost:8081 (assuming port 8081 for User Service).

## API Endpoints

- **POST /signup**  
  Sign up a new user.

- **POST /login**  
  Authenticate a user and generate a token.

- **POST /logout**  
  Log out a user and invalidate the token.

- **GET /validate-token**  
  Validate the provided token.

- **GET /user-details**  
  Retrieve details of the authenticated user.

## Project Structure

- **`src/main/java`**  
  Contains the Java source code for the service.

- **`src/main/resources`**  
  Contains configuration files such as `application.properties`.

- **`src/test/java`**  
  Contains unit and integration tests.

## Additional Notes

- The User Service supports OAuth 2 for secure authorization and token management.
- It is designed to be stateless and scalable, suitable for high-traffic environments.
- Make sure to configure OAuth 2 clients properly in `application.properties` for different environments.

## Related Services

- **[Product Service](https://github.com/AmanPr01/E-Commerce)**: Manages product information and operations.
- **[Email Service](https://github.com/AmanPr01/EmailService)**: Handles asynchronous email notifications.
- **[Payment Service](https://github.com/AmanPr01/PaymentService)**: Manages payment processing and transactions.
   
