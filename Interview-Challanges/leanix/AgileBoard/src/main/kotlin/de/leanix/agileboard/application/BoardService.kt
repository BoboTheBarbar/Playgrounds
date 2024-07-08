package de.leanix.agileboard.application

import Board
import de.leanix.agileboard.adapter.persistence.dto.toBoard
import de.leanix.agileboard.application.persistence.BoardRepository
import org.springframework.stereotype.Service

@Service
class BoardService (private val boardRepository: BoardRepository) {

    fun getAllBoards(): List<Board> = boardRepository.findAll().map { it.toBoard() }
}