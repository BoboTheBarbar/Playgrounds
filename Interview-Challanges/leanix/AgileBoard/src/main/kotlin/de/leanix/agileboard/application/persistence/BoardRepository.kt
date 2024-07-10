package de.leanix.agileboard.application.persistence

import Board
import java.util.*

interface BoardRepository {
    fun findAllBoards(): List<Board>
    fun saveBoard(board: Board) : Board
    fun findBoardById(id: UUID): Board
}