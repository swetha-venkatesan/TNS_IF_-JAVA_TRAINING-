# EvenTix — Event Ticketing & Registration System

**EvenTix** is a microservices-based backend system for event organizers. It demonstrates:
- Microservices using Spring Boot (Java)
- Service discovery with Eureka
- API Gateway routing (Spring Cloud Gateway)
- JWT-based authentication and authorization
- Containerization with Docker
- docker-compose for local orchestration

## Project layout (simplified)
- eureka-server/ (Spring Boot Eureka server)
- api-gateway/ (Spring Cloud Gateway, routes requests)
- auth-service/ (Authentication, issues JWTs)
- event-service/ (Create/read events)
- registration-service/ (Register attendees)
- ticket-service/ (Generate tickets)
- notification-service/ (Send notifications)
- common/ (shared JWT utils; optional library)
- docker-compose.yml (to run everything locally)

> This repository contains **minimal, runnable skeletons** of each service (source, Dockerfile, pom.xml, application.properties). You can import each folder as a Maven project and run them, or build Docker images with the provided Dockerfiles.

## Quick start (local)
1. Ensure Java 17+, Maven, Docker, and docker-compose installed.
2. From the project root build images:
```bash
# example: build all images (or use mvn package inside each service)
docker-compose build
docker-compose up
```
3. Access:
- Eureka dashboard: http://localhost:8761
- API Gateway (example): http://localhost:8080/actuator/health

## Security
- Auth service issues JWTs at `POST /auth/login` (credentials: `user:password` in demo).
- Other services accept `Authorization: Bearer <token>` and validate signature using a shared secret defined in properties.

## Architecture diagram
See `architecture/architecture.puml` for a PlantUML diagram of system components.

## Notes
- This is a reference implementation intended for demonstration and further extension.
- For production use: secure secrets, use TLS, certificate-based auth, centralized config, monitoring, and production-grade messaging.

