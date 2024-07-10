package de.leanix.agileboard.adapter.web

import de.leanix.agileboard.BehaviorSpecIT
import de.leanix.agileboard.adapter.web.dto.*
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode
import java.util.*

class TaskControllerIT () : BehaviorSpecIT() {
    init {
        Context("It is possible to update a Task") {
            Given("A board exists in the system with a task") {
                val createdBoard = addBoard()

                val taskWebRequestDTO = CreateTaskWebRequestDTO(name = "New Task", description = null, userId = UUID.randomUUID())

                val createdTask = restClient.exchange<TaskWebResponseDTO>(
                    "$baseUrl/boards/${createdBoard.id}/tasks",
                    HttpMethod.POST,
                    HttpEntity(taskWebRequestDTO)
                ).body ?: throw IllegalStateException("Task creation failed")

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
    }

}
