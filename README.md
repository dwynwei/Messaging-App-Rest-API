# Messaging App on Google Cloud Platform (GCP)

## Overview

This Messaging App is a robust, scalable cloud-based application deployed on Google Cloud Platform (GCP). It leverages GCP's powerful features to deliver a secure and efficient messaging experience, developed using Java 17 and Spring Boot 3.2.1.

## Key Features

- User authentication and management.
- Message creation, retrieval, updating, and deletion.
- Group chat functionality.
- OAuth2 authentication with Google.
- Data storage in GCP Datastore.

## API Endpoints

### Message Controller

- **Create Message**  
  `POST /api/messages`  
  Creates a new message.

- **Get Message**  
  `GET /api/messages/{messageId}`  
  Retrieves a message by ID.

- **Get All Messages**  
  `GET /api/messages`  
  Retrieves all messages.

- **Update Message**  
  `PUT /api/messages/{messageId}`  
  Updates a specific message.

- **Delete Message**  
  `DELETE /api/messages/{messageId}`  
  Deletes a specific message.

### User Controller

- **Create User**  
  `POST /users`  
  Registers a new user.

- **Get User**  
  `GET /users/{id}`  
  Retrieves a user by ID.

- **Get All Users**  
  `GET /users`  
  Retrieves all users.

- **Update User**  
  `PUT /users/{id}`  
  Updates user details.

- **Delete User**  
  `DELETE /users/{id}`  
  Deletes a user.

- **Add Role to User**  
  `POST /users/{userId}/roles/{role}`  
  Assigns a new role to a user.

- **Update User Roles**  
  `PUT /users/{userId}/roles`  
  Updates roles of a user.

### User Authentication Controller

- **Register/Update User**  
  `POST /api/users`  
  Handles user registration or update.

- **Login User**  
  `POST /api/users/login`  
  Manages user login.

- **Logout User**  
  `POST /api/users/logout`  
  Handles user logout.

- **Check Token Expiry**  
  `GET /api/users/token-expired`  
  Checks if a user's token is expired.

## Application Properties

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            # ... OAuth2 and Google Client Configuration
  cloud:
    gcp:
      datastore:
        namespace: messaging-app-namespace
      project-id: [GCP Project ID]
      credentials:
        location: classpath:keys/GOOGLE_APPLICATION_CREDENTIALS.json
  mail:
    # ... SMTP Mail Server Configuration
springdoc:
  swagger-ui:
    enabled: true
server:
  port: 8085
gcp:
  # ... GCP Specific Configurations
api:
  key: "1234567890"
  expected-key: "1234567890"
```
  
## Running the Application

To run the Messaging App on your local environment, follow these steps:

1. **Clone the Repository**:  
````bash
git clone https://github.com/yourusername/Messaging-app-gcp.git
````

## Navigate to the project directory:
````bash
cd Messaging-app-gcp
````


2. **Install Dependencies**:  
Ensure you have Java 17 installed. Then, use Maven to install the dependencies:
````bash
mvn clean install
````

3. **Start the Application**:  
Run the application using Spring Boot:
````bash
mvn spring-boot:run
````

Alternatively, if you have packaged the application as a JAR file:
````bash
java -jar target/messagingapp-0.0.1-SNAPSHOT.jar
````


4. **Access the Application**:  
Open your web browser and visit:
[http://localhost:8085](http://localhost:8085)

## Contributing

Contributions to the Messaging App project are always welcome. Please follow these guidelines:

- Adhere to the coding standards and practices established in the project.
- Write clean, maintainable, and efficient code.
- Make sure your code is well-tested and does not introduce new bugs.
- Create a pull request with a clear description of your changes.

## License

This project is licensed under the MIT License. For more details, see the [LICENSE.md](LICENSE.md) file in the repository.

**Developed by dwynwei**  
Connect with me on [LinkedIn](https://www.linkedin.com/in/cagsahin/)
