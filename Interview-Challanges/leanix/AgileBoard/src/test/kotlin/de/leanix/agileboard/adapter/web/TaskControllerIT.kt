package de.leanix.agileboard.adapter.web

import Board
import de.leanix.agileboard.BehaviorSpecIT
import de.leanix.agileboard.adapter.web.dto.CreateTaskWebRequestDTO
import de.leanix.agileboard.adapter.web.dto.TaskWebResponseDTO
import de.leanix.agileboard.adapter.web.dto.UpdateTaskWebRequestDTO
import de.leanix.agileboard.application.TaskPatchDTO
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode
import java.util.*

class TaskControllerIT() : BehaviorSpecIT() {
    init {
        Context("It is possible to update a Task") {
            Given("A task on a board") {
                val createdTask = createTask()

                When("I update the task at /tasks/{taskId}") {
                    val updatedTask = UpdateTaskWebRequestDTO(
                        "Updated Task",
                        "new description",
                        createdTask.userId,
                        Board.Status.STARTED.name
                    )

                    val result = restClient.exchange<TaskWebResponseDTO>(
                        "$baseUrl/tasks/${createdTask.id}",
                        HttpMethod.PUT,
                        HttpEntity(updatedTask)
                    )

                    Then("it should return OK and the updated task") {
                        result.statusCode shouldBe HttpStatusCode.valueOf(200)
                        result.body?.name shouldBe updatedTask.name
                    }
                }
            }
        }

        xContext("I should be able to patch a Task") {
            Given("A task on a board") {
                val createdTask = createTask()

                When("I patch the task at /tasks/{taskId}") {
                    val updatedTask = TaskPatchDTO(
                        name = null,
                        description = "new description",
                        status = Board.Status.STARTED.name,
                        userId = null
                    )

                    // FIXME: restClient does not support PATCH method -> configure HttpComponentsClientHttpRequestFactory
                    val result = restClient.patchForObject(
                        "$baseUrl/tasks/${createdTask.id}",
                        HttpEntity(updatedTask),
                        TaskWebResponseDTO::class.java
                    )

                    Then("it should return OK and the updated task") {
                        result.name shouldNotBe updatedTask.name
                        result.description shouldBe updatedTask.description
                    }
                }
            }
        }

        Context("It is possible to delete a Task") {
            Given("A task on a board") {
                val createdTask = createTask()

                When("I delete the task at /tasks/{taskId}") {
                    val result = restClient.exchange<Void>(
                        "$baseUrl/tasks/${createdTask.id}",
                        HttpMethod.DELETE
                    )

                    Then("it should return NO_CONTENT") {
                        result.statusCode shouldBe HttpStatusCode.valueOf(204)
                    }
                }
            }
        }
    }

    private fun createTask(): TaskWebResponseDTO {
        val createdBoard = addBoard()

        val taskWebRequestDTO =
            CreateTaskWebRequestDTO(name = "New Task", description = null, userId = UUID.randomUUID())

        val createdTask = restClient.exchange<TaskWebResponseDTO>(
            "$baseUrl/boards/${createdBoard.id}/tasks",
            HttpMethod.POST,
            HttpEntity(taskWebRequestDTO)
        ).body ?: throw IllegalStateException("Task creation failed")
        return createdTask
    }

}
