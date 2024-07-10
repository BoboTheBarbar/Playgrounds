package de.leanix.agileboard.application

import Board
import Board.Task
import de.leanix.agileboard.application.persistence.BoardRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.util.*

class TaskServiceTest : BehaviorSpec({

    val boardRepository = mockk<BoardRepository>()
    val boardService = TaskService(boardRepository)

    Context("service should be able to update a Task") {
        Given("an board exists with a task and a task to update") {
            val originalTask = Task(UUID.randomUUID(), "Task 1", user = UUID.randomUUID())
            val boardWithTask = Board(UUID.randomUUID(), "Board 2", null, listOf(originalTask))
            val updatedTask = originalTask.copy(name = "Task 2", description = "Task 2 Description")

            val savedBoardSlot = slot<Board>()
            coEvery { boardRepository.findAllBoards() } returns listOf(createBoard(), boardWithTask)
            coEvery { boardRepository.saveBoard(capture(savedBoardSlot)) } returns boardWithTask.copy(
                tasks = listOf(
                    updatedTask
                )
            )

            When("the service is told to update a Task") {
                val taskResponse = boardService.updateTask(updatedTask)

                Then("it should update the Task") {
                    verify { boardRepository.saveBoard(any()) }
                    savedBoardSlot.captured.tasks.size shouldBe boardWithTask.tasks.size
                    taskResponse shouldBe updatedTask
                }
            }
        }
    }

    Context("service should be able to do partially update (patch) of a Task") {
        Given("an board exists with a task and a task to update") {
            val originalTask = Task(UUID.randomUUID(), "Task 1", user = UUID.randomUUID())
            val boardWithTask = Board(UUID.randomUUID(), "Board 2", null, listOf(originalTask))
            val updatedTask = originalTask.copy(name = "Task 2", description = "Task 2 Description")

            val savedBoardSlot = slot<Board>()
            coEvery { boardRepository.findAllBoards() } returns listOf(createBoard(), boardWithTask)
            coEvery { boardRepository.saveBoard(capture(savedBoardSlot)) } returns boardWithTask.copy(
                tasks = listOf(
                    updatedTask
                )
            )

            When("the service is told to patch a Task partially") {
                val updateTaskPartialDTO =
                    UpdateTaskPartialDTO(
                        name = null,
                        description = updatedTask.description,
                        status = updatedTask.status?.name,
                        userId = updatedTask.user
                    )
                val taskResponse = boardService.updateTaskPartially(updatedTask.id, updateTaskPartialDTO)

                Then("it should only update the fields that are not null in the DTO") {
                    verify { boardRepository.saveBoard(any()) }
                    savedBoardSlot.captured.tasks.size shouldBe boardWithTask.tasks.size
                    taskResponse.name shouldNotBe updatedTask.name
                    taskResponse.description shouldBe updatedTask.description
                }
            }
        }
    }

})

private fun createBoard(): Board = Board(UUID.randomUUID(), "Board 1")
