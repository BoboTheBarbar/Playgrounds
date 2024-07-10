package de.leanix.agileboard.application

import Board
import de.leanix.agileboard.application.persistence.BoardRepository
import org.springframework.stereotype.Service
import java.util.*

// TODO: Add Controller Advice for Exception Handling

@Service
class BoardService(private val boardRepository: BoardRepository) {

    fun getAllBoards(): List<Board> = boardRepository.findAllBoards()

    fun addBoard(board: Board): Board = boardRepository.saveBoard(board)

    fun getBoard(id: UUID): Board = boardRepository.findBoardById(id)

    fun deleteBoard(id: UUID) = boardRepository.deleteBoardById(id)

    fun addTaskToBoard(task: Board.Task, boardId: UUID): Board.Task {
        try {
            val board = boardRepository.findBoardById(boardId)
            val updatedBoard = board.copy(tasks = board.tasks + task)
            boardRepository.saveBoard(updatedBoard)
            return updatedBoard.tasks.first { it.id == task.id }
        } catch (e: NoSuchElementException) {
            throw IllegalArgumentException(e)
        }
    }

    fun updateTask(task: Board.Task): Board.Task {
        val matchingBoard = findMatchingBoard(task.id)
        val updatedBoard = matchingBoard.copy(tasks = matchingBoard.tasks.map { if (it.id == task.id) task else it })
        boardRepository.saveBoard(updatedBoard)

        return updatedBoard.tasks.first { it.id == task.id }
    }

    fun updateTaskPartially(taskId: UUID, updateTaskPartialDTO: UpdateTaskPartialDTO): Board.Task {
        val matchingBoard = findMatchingBoard(taskId)

        val task = matchingBoard.tasks.first { it.id == taskId }
        val updatedTask = task.copy(
            name = updateTaskPartialDTO.name ?: task.name,
            description = updateTaskPartialDTO.description ?: task.description,
            status = updateTaskPartialDTO.status?.let { Board.Status.valueOf(it) } ?: task.status,
            user = updateTaskPartialDTO.userId ?: task.user
        )

        val updatedBoard = matchingBoard.copy(tasks = matchingBoard.tasks.map { if (it.id == taskId) updatedTask else it })
        boardRepository.saveBoard(updatedBoard)

        return updatedBoard.tasks.first { it.id == taskId }
    }

    private fun findMatchingBoard(taskId: UUID): Board {
        return boardRepository.findAllBoards().firstOrNull { board ->
            board.tasks.any { it.id == taskId }
        } ?: throw NoSuchElementException("Task not found")
    }
}