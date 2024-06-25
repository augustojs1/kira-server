# 🏬 Kira Server

#### Kira is a project management tool inspired by Jira/Trello where users can create and manage their team, board, tasks and follow up on their project status. Created using Java 17, Spring Boot 6, Spring Data JPA, MySQL and Docker.

- [Table of Content](#table-of-content)
- [Non-Functional Requirements](#non-functional-requirements)
- [Kira Database Diagram](#kira-database-diagram)
- [How To Run](#how-to-run)
    - [Requirements](#functional-requirements)
    - [Running Kira Server](#-running-kira-server)

### Non-Functional Requirements

- [x] Kira Server should be developed using the following technologies: Java 17, Spring Boot, MySQL and Docker.
- [x] Timestamp properties in MySQL tables.
- [x] REST API should use prefix: '/api/v1/'
- [x] Should use JWT for authentication.
- [x] Docker container for the database.
- [x] Swagger OpenApi documentation.
- [x] Docker container for the application.

### Functional Requirements
[Requirements documentation](docs/requirements.md)

### Kira Database Diagram
<img title="kira database design" alt="kira database design" src="docs/kira_database_diagram.png">

### Requirements

Before you start, you should have installed in your machine the following tools:
[Git](https://git-scm.com), [Java](https:///) and [Docker](https://www.docker.com/). Preferably Java version >= 17.
To edit the code you can use a code editor like [VSCode](https://code.visualstudio.com/) or [InteliJ](https://www.jetbrains.com/pt-br/idea/).

### 🚀 Running Kira Server

- Clone this repository:
```bash
git clone git@github.com:augustojs1/kira-server
```
- Go to application.properties file and fill in the application environment variables:
```bash
server.port=8080 # Default port
app.env.jwt-secret-key= # Generate a valid key
app.env.expiration-time= # Setup expiration time for the JWT token
spring.datasource.url=jdbc:mysql://database:3306/kira_database # Default MySQL Docker container datasource URL
spring.datasource.username=kira_root  # Default MySQL Docker container username
spring.datasource.password=kirapw # Default MySQL Docker container password
```

- You can start the Docker containers running:
```bash
docker compose up
```

- By default application runs in the following port and path: http://localhost:8080/api/v1/ 
- Swagger OpenAPI documentation can be found at: http://localhost:8080/api/v1/swagger-ui/index.html#/
