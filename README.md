# Eclair Admin Server

Production-ready backend API for **Eclair**, a stage lighting assistant mobile app built with Kotlin Multiplatform. This server provides a REST API for managing and delivering lighting training content organized by concepts like Stage Basics, Concert Lighting, Theatre, Equipment, Colour Theory, and DMX.

## Features

- **REST API** with JWT authentication
- **Admin portal** for content management
- **Content versioning** system with `updatedAt` timestamps
- **PostgreSQL database** with Flyway migrations
- **Structured content** with sections and mixed text/image content
- **Role-based access control** (Admin/User roles)
- **OpenAPI/Swagger documentation**
- **Health checks** and monitoring via Actuator
- **Production-ready** with Docker support

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Security** with JWT
- **Spring Data JPA**
- **PostgreSQL 15**
- **Flyway** for database migrations
- **Lombok** for boilerplate reduction
- **SpringDoc OpenAPI** for API documentation
- **Maven** for dependency management

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 15+ (or use Docker Compose)
- Docker (optional, for containerized deployment)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd eclair_admin_server
```

### 2. Database Setup

#### Option A: Using Docker Compose (Recommended)

```bash
docker-compose up -d postgres
```

This will start PostgreSQL on `localhost:5432` with:
- Database: `eclair_db`
- Username: `postgres`
- Password: `postgres`

#### Option B: Manual PostgreSQL Setup

1. Install PostgreSQL 15+
2. Create database:
```sql
CREATE DATABASE eclair_db;
```

### 3. Configure Environment Variables

Create a `.env` file or set the following environment variables:

```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/eclair_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres
JWT_SECRET=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
JWT_EXPIRATION=86400000
```

### 4. Run the Application

#### Using Maven

```bash
./mvnw spring-boot:run
```

#### Using Docker Compose (Full Stack)

```bash

```

The application will be available at `http://localhost:8080`

### 5. Access API Documentation

Once running, visit:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## Default Admin Credentials

The database migration creates a default admin account:

- **Username**: `admin`
- **Password**: `admin123`

**IMPORTANT**: Change this password in production!

## API Endpoints

### Public Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/concepts` | List all published concepts |
| GET | `/api/v1/concepts/{id}` | Get detailed concept content |

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/login` | Admin login (returns JWT token) |

### Admin Endpoints (Requires Authentication)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/admin/concepts` | List all concepts (including unpublished) |
| GET | `/api/v1/admin/concepts/{id}` | Get concept by ID |
| POST | `/api/v1/admin/concepts` | Create new concept |
| PATCH | `/api/v1/admin/concepts/{id}` | Update concept |
| DELETE | `/api/v1/admin/concepts/{id}` | Delete concept |

## API Usage Examples

### 1. Login as Admin

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "admin",
  "role": "ADMIN"
}
```

### 2. Get Published Concepts (Public)

```bash
curl http://localhost:8080/api/v1/concepts
```

Response:
```json
[
  {
    "id": 1,
    "title": "Stage Basics",
    "description": "Fundamental concepts of stage lighting",
    "displayOrder": 1,
    "published": true,
    "updatedAt": "2025-11-13T10:30:00",
    "version": 0
  }
]
```

### 3. Get Concept Details (Public)

```bash
curl http://localhost:8080/api/v1/concepts/1
```

Response:
```json
{
  "id": 1,
  "title": "Stage Basics",
  "description": "Fundamental concepts of stage lighting",
  "sections": [
    {
      "id": 1,
      "heading": "Introduction to Stage Lighting",
      "content": [
        {
          "type": "text",
          "value": "Stage lighting is the craft of illuminating performers..."
        },
        {
          "type": "image",
          "value": "https://example.com/stage-lighting.jpg"
        }
      ]
    }
  ],
  "updatedAt": "2025-11-13T10:30:00",
  "version": 0
}
```

### 4. Create New Concept (Admin)

```bash
curl -X POST http://localhost:8080/api/v1/admin/concepts \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Colour Theory",
    "description": "Understanding color in stage lighting",
    "displayOrder": 3,
    "published": false,
    "sections": [
      {
        "heading": "Primary Colors",
        "displayOrder": 1,
        "content": [
          {
            "type": "text",
            "value": "The primary colors of light are red, green, and blue (RGB).",
            "displayOrder": 1
          },
          {
            "type": "image",
            "value": "https://example.com/rgb-wheel.jpg",
            "displayOrder": 2
          }
        ]
      }
    ]
  }'
```

### 5. Update Concept (Admin)

```bash
curl -X PATCH http://localhost:8080/api/v1/admin/concepts/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Stage Basics - Updated",
    "description": "Updated description",
    "published": true
  }'
```

## Database Schema

### Users Table
- `id` (BIGSERIAL, PK)
- `username` (VARCHAR, UNIQUE)
- `email` (VARCHAR, UNIQUE)
- `password` (VARCHAR, BCrypt hashed)
- `role` (ENUM: ADMIN, USER)
- `enabled` (BOOLEAN)
- `created_at`, `updated_at` (TIMESTAMP)

### Concepts Table
- `id` (BIGSERIAL, PK)
- `title` (VARCHAR)
- `description` (TEXT)
- `display_order` (INTEGER)
- `published` (BOOLEAN)
- `created_at`, `updated_at` (TIMESTAMP)
- `version` (BIGINT) - for optimistic locking

### Sections Table
- `id` (BIGSERIAL, PK)
- `heading` (VARCHAR)
- `display_order` (INTEGER)
- `concept_id` (FK to Concepts)
- `created_at`, `updated_at` (TIMESTAMP)

### Content Items Table
- `id` (BIGSERIAL, PK)
- `type` (ENUM: TEXT, IMAGE)
- `value` (TEXT)
- `display_order` (INTEGER)
- `section_id` (FK to Sections)
- `created_at`, `updated_at` (TIMESTAMP)

## Content Versioning

The API includes automatic content versioning:

- Every concept has an `updatedAt` timestamp
- Mobile apps can check for updates by comparing timestamps
- The `version` field provides optimistic locking for concurrent updates

Example mobile app update check:
```kotlin
val lastSync = preferences.getLastSyncTime()
val concepts = api.getConcepts()
val updatedConcepts = concepts.filter { it.updatedAt > lastSync }
```

## Deployment

### Deploy to Fly.io

1. Install Fly CLI:
```bash
curl -L https://fly.io/install.sh | sh
```

2. Login and create app:
```bash
fly auth login
fly launch --name eclair-admin-server
```

3. Create PostgreSQL database:
```bash
fly postgres create --name eclair-postgres
fly postgres attach eclair-postgres
```

4. Set secrets:
```bash
fly secrets set JWT_SECRET=$(openssl rand -base64 32)
fly secrets set JWT_EXPIRATION=86400000
```

5. Deploy:
```bash
fly deploy
```

### Deploy to Render

1. Create account at https://render.com
2. Connect your GitHub repository
3. Use the `render.yaml` configuration file (already included)
4. Render will automatically:
   - Create PostgreSQL database
   - Build and deploy the application
   - Set up environment variables

### Environment Variables for Production

Set these environment variables in your deployment platform:

```bash
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=<your-postgres-connection-string>
DATABASE_USERNAME=<your-db-username>
DATABASE_PASSWORD=<your-db-password>
JWT_SECRET=<generate-secure-random-string>
JWT_EXPIRATION=86400000
PORT=8080
```

Generate a secure JWT secret:
```bash
openssl rand -base64 64
```

## Project Structure

```
eclair_admin_server/
├── src/
│   ├── main/
│   │   ├── java/com/judahben149/eclair/
│   │   │   ├── config/           # Configuration classes
│   │   │   ├── controller/       # REST controllers
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── exception/        # Exception handling
│   │   │   ├── mapper/           # Entity-DTO mappers
│   │   │   ├── model/            # JPA entities
│   │   │   ├── repository/       # Spring Data repositories
│   │   │   ├── security/         # JWT and security
│   │   │   ├── service/          # Business logic
│   │   │   └── EclairApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       └── db/migration/     # Flyway migrations
│   └── test/                     # Test classes
├── Dockerfile
├── docker-compose.yml
├── fly.toml
├── render.yaml
├── pom.xml
└── README.md
```

## Testing

Run tests:
```bash
./mvnw test
```

Run with coverage:
```bash
./mvnw clean verify
```

## Monitoring

Health check endpoint:
```bash
curl http://localhost:8080/actuator/health
```

## Security Notes

1. **Change default admin password** immediately in production
2. **Use strong JWT secret** - generate with `openssl rand -base64 64`
3. **Enable HTTPS** in production (handled by Fly.io/Render)
4. **Rotate JWT tokens** periodically
5. **Review CORS settings** in `SecurityConfig.java` for your domain

## Troubleshooting

### Database Connection Issues

Check connection string format:
```
jdbc:postgresql://host:port/database
```

Verify PostgreSQL is running:
```bash
docker-compose ps
```

### Migration Failures

Reset Flyway (development only):
```bash
./mvnw flyway:clean flyway:migrate
```

### JWT Token Issues

Verify token is included in Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## License

This project is licensed under the MIT License.

## Support

For issues or questions:
- Open an issue in the repository
- Email: support@eclair.app

---

Built with Spring Boot for the Eclair Stage Lighting Assistant
