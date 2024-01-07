# Spring Boot Chat Application

This repository hosts the code for a chat application that utilizes Spring Boot for backend services and includes a client application. The backend is split into two services: `auth-service` for managing user authentication, and `chat-service` for managing chat messages. The client application provides a basic web interface built using React. All services are containerized using Docker and managed with Docker Compose. The application is accessed through a single entry point on port 80, which is managed by Nginx. Nginx serves the static files for the frontend application and also functions as an API gateway, managing communication with and between the backend services.

## Services

- `auth-service`: Handles user authentication. It uses Spring Security for authentication and JWT for maintaining user sessions.

- `chat-service`: Handles chat messages. It uses MongoDB for storing messages and Redis for caching.

- `chat-app-client`: A simple web interface for the chat application. It is built using React and uses STOMP Client for real-time communication with the chat service.

## Application Architecture Diagram

![diagram](https://github.com/misterneo/spring-microservices-chat-app/assets/25622326/2f5e8edc-c5bc-4725-8f60-b863818b5d19)

## Getting Started

To get started with the application, you need to have Docker and Docker Compose installed on your machine. Once you have these prerequisites, you can clone the repository and use Docker Compose to build and run the application.

## Running the Application

1. Clone the repository:

```sh
git clone https://github.com/misterneo/spring-microservices-chat-app.git
cd spring-microservices-chat-app
```

2. Build the services:

```sh
docker compose build
```

3. Start the application

```
docker compose up -d
```

4. Access the application at `http://localhost`

## License

This project is licensed under the MIT license. See the [LICENSE](https://choosealicense.com/licenses/mit/) file for more details.
