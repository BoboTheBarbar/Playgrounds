# ToDo Service

## Overview
ToDo Service is a task and board management system designed to help users organize and manage their tasks efficiently. It allows users to create boards, add tasks to boards, update and delete tasks, and manage user-related tasks through a centralized user service.

## Features
- **Board Management**: Users can create, retrieve, and delete boards.
- **Task Management**: Users can add tasks to boards, update tasks, partially update tasks, and delete tasks.
- **User Management**: The system supports removing tasks related to a user when the user is deleted from the Centralized User Service.

## Getting Started

### IDE-Prerequisites
- JDK 17
- Gradle
- docker / podman / ...

### Running the Application Locally

Boards and task will be stored in a H2 in-memory database or can be configured to use your custom db like PostgreSQL.

To start the application, run the following command:

```shell
./gradlew bootRun
```

or use the docker-compose file to start the application and/or the database:

```shell
docker-compose up
```

### Generated domain classes

The domain classes are generated from [the schema files](src/main/resources/schema/board-schema.json) during the `:build` or `:generate` task. Gradle uses json-kotlin-schema-gen plugin for this.

> Configuration option for [plugin-source](https://github.com/pwall567/json-kotlin-gradle?tab=readme-ov-file#to-use).

The plugin follows the principle of `convention over configuration`. The default location for the schema file or files to be input to the generation process is `/src/main/resources/schema`.

## Example requests

Example requests can be found in the [requests](src/test/resources/http/BoardRequestExamples.http) folder.

## API Documentation

The API documentation is available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).
