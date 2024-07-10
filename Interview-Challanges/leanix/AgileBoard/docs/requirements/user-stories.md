# ToDo Service User Stories

## Board Management
- [x] **User Story 1:** As a user, I want to retrieve a list of all boards, so that I can view all the boards I have created.
    - **Endpoint:** `GET /boards`
    - **Acceptance Criteria:**
        - The endpoint returns a list of all boards.
        - The response includes the `id`, `name`, and `description` of each board.

- [x] **User Story 2:** As a user, I want to create a new board, so that I can organize my tasks.
    - **Endpoint:** `POST /boards`
    - **Acceptance Criteria:**
        - The endpoint accepts `name` and `description` as attributes.
        - A new board is created with a unique `id`.
        - The response includes the `id`, `name`, and `description` of the newly created board.

- [x] **User Story 3:** As a user, I want to retrieve a specific board along with its tasks, so that I can view all tasks associated with the board.
    - **Endpoint:** `GET /boards/{id}`
    - **Acceptance Criteria:**
        - The endpoint returns the board details including all associated tasks.
        - Task details are enriched with user information retrieved from the Centralized User Service.

- [x] **User Story 4:** As a user, I want to delete a board, so that I can remove boards that are no longer needed.
    - **Endpoint:** `DELETE /boards/{id}`
    - **Acceptance Criteria:**
        - The specified board is deleted.
        - The response confirms the deletion of the board.

## Task Management
- [ ] **User Story 5:** As a user, I want to add a task to a board, so that I can manage specific tasks within the board.
    - **Endpoint:** `POST /boards/{id}/tasks`
    - **Acceptance Criteria:**
        - The endpoint accepts `name`, `description`, and `user` attributes.
        - A new task is created with a unique `id` and status set to `CREATED`.
        - The response includes the `id`, `name`, `description`, `user`, and `status` of the newly created task.

- [ ] **User Story 6:** As a user, I want to update an existing task, so that I can modify the details of a task.
    - **Endpoint:** `PUT /tasks/{id}`
    - **Acceptance Criteria:**
        - The endpoint accepts updated task attributes.
        - The specified task is overwritten with the new details.
        - The response includes the updated task details.

- [ ] **User Story 7:** As a user, I want to partially update an existing task, so that I can modify specific attributes of a task.
    - **Endpoint:** `PATCH /tasks/{id}`
    - **Acceptance Criteria:**
        - The endpoint accepts partial task attributes.
        - The specified task is updated with the new attributes.
        - The response includes the updated task details.

- [ ] **User Story 8:** As a user, I want to delete a task, so that I can remove tasks that are no longer needed.
    - **Endpoint:** `DELETE /tasks/{id}`
    - **Acceptance Criteria:**
        - The specified task is deleted.
        - The response confirms the deletion of the task.

## User Management
- [ ] **User Story 9:** As a system, I want to remove all tasks related to a user when the user is deleted from the Centralized User Service, so that orphan tasks are automatically cleaned up.
    - **Webhook Endpoint:** `POST /webhooks/user-deleted`
    - **Acceptance Criteria:**
        - The endpoint receives a payload with the `user` id.
        - All tasks associated with the specified user are deleted.
        - The response confirms the successful handling of the webhook.

## Additional Considerations
- [x] **User Story 10:** As a developer, I want to containerize the ToDo Service using Docker, so that it can be easily deployed and run in various environments.
    - **Acceptance Criteria:**
        - A Dockerfile is created to build the ToDo Service container.
        - The Docker container is published to a container registry.
        - A `docker-compose.yml` file is provided for running the service locally.

- [ ] **User Story 11:** As a developer, I want to include automated tests, CI/CD pipelines, and documentation, so that the service is reliable, maintainable, and well-documented.
    - **Acceptance Criteria:**
        - Automated tests are created for the service endpoints.
        - CI/CD pipelines are set up for automated building, testing, and deployment.
        - Comprehensive documentation is provided for the service, including setup instructions, API documentation, and architectural details.

