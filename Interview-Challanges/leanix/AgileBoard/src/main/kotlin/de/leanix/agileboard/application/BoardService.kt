package de.leanix.agileboard.application

import Board
import de.leanix.agileboard.application.persistence.BoardRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BoardService (private val boardRepository: BoardRepository) {

    fun getAllBoards(): List<Board> = boardRepository.findAllBoards()

    fun addBoard(board: Board): Board = boardRepository.saveBoard(board)
    fun getBoard(id: UUID): Board = boardRepository.findBoardById(id)
}