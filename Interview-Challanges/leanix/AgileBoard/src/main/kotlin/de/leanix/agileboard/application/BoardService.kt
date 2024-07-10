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
        val boards = boardRepository.findAllBoards()
        val matchingBoard = boards.firstOrNull { board -> board.tasks.any { it.id == task.id } }
            ?: throw NoSuchElementException("Task not found")

        val updatedBoard = matchingBoard.copy(tasks = matchingBoard.tasks.map { if (it.id == task.id) task else it })
        boardRepository.saveBoard(updatedBoard)

        return updatedBoard.tasks.first { it.id == task.id }
    }
}