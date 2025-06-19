🚀 Development of a Bank Card Management System

📝 Task description
- Creation and management of cards
– Viewing Cards
- Transfers between cards

  💳 Card attributes
– Card number (encrypted, displayed as masked value: **** **** **** 1234)
- Cardholder
– Expiration Date
- Status: Active, Blocked, Expired
- Balance

  🧾 Requirements
  ✅ Authentication and Authorization
- Spring Security + JWT
- Roles: ADMIN и USER

  ✅ Features
Administrator:
- Creates, blocks, activates ,and deletes cards
- Manages users
- Can view all cards

  User:
- View their own cards (with search + pagination)
- Requests card blocking
- Makes transfers between their own cards
- Views balance

  ✅ API
- CRUD operations for cards
– Transfers between user`s own cards
– Filtering and pagination
– Validation and error messages

  ✅ Security
– Data encryption
– Role-based access
– Card number masking

✅ Database Integration
- PostgreSQL or MySQL
– Migration using Liquibase(src/main/resources/db/migration)

  ✅ Documentation
- Swagger UI / OpenAPI — docs/openapi.yaml
- README.md with launch instructions

  ✅ Deployment and Testing
- Docker Compose for the development environment
- Liquibase migrations
– Unit tests for key business logic

  💡 Technologies
  Java 17+, Spring Boot, Spring Security, Spring Data JPA, PostgreSQL/MySQL, Liquibase, Docker, JWT, Swagger (OpenAPI)