package de.leanix.agileboard.adapter.persistence

import Board
import de.leanix.agileboard.adapter.persistence.dto.BoardDBAdapter
import de.leanix.agileboard.adapter.persistence.dto.BoardDBDto
import de.leanix.agileboard.application.persistence.BoardRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class BoardRepositoryImpl(val boardDBDtoRepository: BoardDBDtoRepository, val boardDBAdapter: BoardDBAdapter) :
    BoardRepository {
    override fun findAllBoards(): List<Board> {
        return boardDBDtoRepository.findAll().map { boardDBAdapter.toBoard(it) }
    }

    override fun saveBoard(board: Board): Board {
        val boardDBDto = boardDBDtoRepository.save(boardDBAdapter.toBoardDBDto(board))
        return boardDBAdapter.toBoard(boardDBDto)
    }

    override fun findBoardById(id: UUID): Board {
        val dbDto = boardDBDtoRepository.findById(id)
            .orElseThrow { NoSuchElementException("Board with id $id not found") }
        return boardDBAdapter.toBoard(dbDto)
    }

    override fun deleteBoardById(id: UUID) {
        boardDBDtoRepository.deleteById(id)
    }

}

interface BoardDBDtoRepository : CrudRepository<BoardDBDto, UUID>