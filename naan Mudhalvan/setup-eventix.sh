#!/bin/bash

# EvenTix Project Setup Script
# This script creates the complete project structure

set -e

echo "======================================"
echo "Creating EvenTix Project Structure"
echo "======================================"

PROJECT_NAME="eventix"

# Create main project directory
mkdir -p $PROJECT_NAME
cd $PROJECT_NAME

# Create parent pom.xml
cat > pom.xml << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
    </parent>
    <groupId>com.eventix</groupId>
    <artifactId>eventix-parent</artifactId>
    <version>1.0.0</version>
    <packaging>pom</packaging>
    <modules>
        <module>eureka-server</module>
        <module>api-gateway</module>
        <module>event-service</module>
        <module>registration-service</module>
        <module>ticket-service</module>
        <module>notification-service</module>
    </modules>
</project>
EOF

# Create directory structure for all services
services=("eureka-server" "api-gateway" "event-service" "registration-service" "ticket-service" "notification-service")

for service in "${services[@]}"; do
    echo "Creating structure for $service..."
    mkdir -p $service/src/main/java/com/eventix/${service//-/}
    mkdir -p $service/src/main/resources
    mkdir -p $service/src/test/java/com/eventix/${service//-/}
done

# Create Docker Compose file
cat > docker-compose.yml << 'EOF'
version: '3.8'
services:
  postgres:
    image: postgres:15-alpine
    container_name: eventix-postgres
    environment:
      POSTGRES_USER: eventix
      POSTGRES_PASSWORD: eventix123
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./init-db.sql:/docker-entrypoint-initdb.d/init-db.sql
    networks:
      - eventix-network

  eureka-server:
    build: ./eureka-server
    container_name: eventix-eureka
    ports:
      - "8761:8761"
    networks:
      - eventix-network

  api-gateway:
    build: ./api-gateway
    container_name: eventix-gateway
    ports:
      - "8080:8080"
    environment:
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
    depends_on:
      - eureka-server
    networks:
      - eventix-network

  event-service:
    build: ./event-service
    container_name: eventix-event-service
    ports:
      - "8081:8081"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/eventix_events
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
    depends_on:
      - postgres
      - eureka-server
    networks:
      - eventix-network

  registration-service:
    build: ./registration-service
    container_name: eventix-registration-service
    ports:
      - "8082:8082"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/eventix_registrations
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
    depends_on:
      - postgres
      - eureka-server
    networks:
      - eventix-network

  ticket-service:
    build: ./ticket-service
    container_name: eventix-ticket-service
    ports:
      - "8083:8083"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/eventix_tickets
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
    depends_on:
      - postgres
      - eureka-server
    networks:
      - eventix-network

  notification-service:
    build: ./notification-service
    container_name: eventix-notification-service
    ports:
      - "8084:8084"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/eventix_notifications
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
    depends_on:
      - postgres
      - eureka-server
    networks:
      - eventix-network

networks:
  eventix-network:
    driver: bridge

volumes:
  postgres_data:
EOF

# Create README
cat > README.md << 'EOF'
# EvenTix - Event Ticketing System

## Quick Start

1. Build all services:
   ```bash
   chmod +x build-all.sh
   ./build-all.sh
   ```

2. Start services:
   ```bash
   chmod +x start-services.sh
   ./start-services.sh
   ```

3. Access services:
   - Eureka: http://localhost:8761
   - API Gateway: http://localhost:8080
   - Swagger UI: http://localhost:8081/swagger-ui.html

## Next Steps

Copy the code from the artifacts into the respective service directories following this structure:

```
eventix/
├── eureka-server/
│   └── src/main/java/com/eventix/eureka/
├── api-gateway/
│   └── src/main/java/com/eventix/gateway/
├── event-service/
│   └── src/main/java/com/eventix/event/
├── registration-service/
│   └── src/main/java/com/eventix/registration/
├── ticket-service/
│   └── src/main/java/com/eventix/ticket/
└── notification-service/
    └── src/main/java/com/eventix/notification/
```

Refer to the complete documentation artifacts for detailed implementation.
EOF

# Create build script
cat > build-all.sh << 'EOF'
#!/bin/bash
set -e
echo "Building all services..."
mvn clean install -DskipTests
for service in eureka-server api-gateway event-service registration-service ticket-service notification-service; do
    cd $service
    mvn clean package -DskipTests
    cd ..
done
echo "Build complete!"
EOF

chmod +x build-all.sh

# Create start script
cat > start-services.sh << 'EOF'
#!/bin/bash
set -e
echo "Starting EvenTix services..."
docker-compose up -d
echo "Services started! Access Eureka at http://localhost:8761"
EOF

chmod +x start-services.sh

# Create stop script
cat > stop-services.sh << 'EOF'
#!/bin/bash
echo "Stopping EvenTix services..."
docker-compose down
echo "Services stopped!"
EOF

chmod +x stop-services.sh

echo ""
echo "======================================"
echo "✅ Project structure created!"
echo "======================================"
echo ""
echo "Next steps:"
echo "1. Copy the Java code from artifacts into respective directories"
echo "2. Copy application.yml files into src/main/resources/"
echo "3. Run ./build-all.sh to build the project"
echo "4. Run ./start-services.sh to start services"
echo ""
echo "Project location: $(pwd)"
echo "======================================"