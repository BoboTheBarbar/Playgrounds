package de.leanix.agileboard.application.persistence

import Board

interface BoardRepository {
    fun findAllBoards(): List<Board>
    fun saveBoard(board: Board) : Board
}