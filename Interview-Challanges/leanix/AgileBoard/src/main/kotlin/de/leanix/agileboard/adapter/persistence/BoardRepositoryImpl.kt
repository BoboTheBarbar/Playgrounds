package de.leanix.agileboard.adapter.persistence

import Board
import de.leanix.agileboard.adapter.persistence.dto.BoardDBAdapter
import de.leanix.agileboard.adapter.persistence.dto.BoardDBDto
import de.leanix.agileboard.application.persistence.BoardRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class BoardRepositoryImpl(val boardDBDtoRepository: BoardDBDtoRepository, val boardDBAdapter: BoardDBAdapter) : BoardRepository {
    override fun findAllBoards(): List<Board> {
        return boardDBDtoRepository.findAll().map { boardDBAdapter.toBoard(it) }
    }

    override fun saveBoard(board: Board) : Board {
        val boardDBDto = boardDBDtoRepository.save(boardDBAdapter.toBoardDBDto(board))
        return boardDBAdapter.toBoard(boardDBDto)
    }

}

interface BoardDBDtoRepository : CrudRepository<BoardDBDto, UUID>