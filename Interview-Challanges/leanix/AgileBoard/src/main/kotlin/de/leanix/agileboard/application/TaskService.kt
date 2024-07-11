package de.leanix.agileboard.application

import Board
import de.leanix.agileboard.application.persistence.BoardRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskService(private val boardRepository: BoardRepository) {

    fun updateTask(task: Board.Task): Board.Task {
        val matchingBoard = findMatchingBoard(task.id)
        val updatedBoard = matchingBoard.copy(tasks = matchingBoard.tasks.map { if (it.id == task.id) task else it })
        boardRepository.saveBoard(updatedBoard)

        return updatedBoard.tasks.first { it.id == task.id }
    }

    fun updateTaskPartially(taskId: UUID, taskPatchDTO: TaskPatchDTO): Board.Task {
        val matchingBoard = findMatchingBoard(taskId)

        val task = matchingBoard.tasks.first { it.id == taskId }
        val updatedTask = task.copy(
            name = taskPatchDTO.name ?: task.name,
            description = taskPatchDTO.description ?: task.description,
            status = taskPatchDTO.status?.let { Board.Status.valueOf(it) } ?: task.status,
            user = taskPatchDTO.userId ?: task.user
        )

        val updatedBoard =
            matchingBoard.copy(tasks = matchingBoard.tasks.map { if (it.id == taskId) updatedTask else it })
        boardRepository.saveBoard(updatedBoard)

        return updatedBoard.tasks.first { it.id == taskId }
    }

    fun deleteTaskById(taskID: UUID) {
        boardRepository.deleteTaskById(taskID)
    }

    private fun findMatchingBoard(taskId: UUID): Board {
        return boardRepository.findAllBoards().firstOrNull { board ->
            board.tasks.any { it.id == taskId }
        } ?: throw NoSuchElementException("Task not found")
    }

    fun deleteTasksByUserId(userId: UUID) {
        boardRepository.findAllBoards().flatMap { it.tasks }
            .filter { it.user == userId }
            .forEach {
                deleteTaskById(it.id)
            }
    }
}