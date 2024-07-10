package de.leanix.agileboard.adapter.persistence

import Board
import de.leanix.agileboard.adapter.persistence.dto.BoardDBAdapter
import de.leanix.agileboard.adapter.persistence.dto.BoardDBDto
import de.leanix.agileboard.application.persistence.BoardRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class BoardRepositoryRDBMS(val boardDBCrudRepository: BoardDBCrudRepository, val boardDBAdapter: BoardDBAdapter) : BoardRepository {
    override fun findAllBoards(): List<Board> {
        return boardDBCrudRepository.findAll().map { boardDBAdapter.toBoard(it) }
    }

    override fun saveBoard(board: Board): Board {
        val boardDBDto = boardDBCrudRepository.save(boardDBAdapter.toBoardDBDto(board))
        return boardDBAdapter.toBoard(boardDBDto)
    }

    override fun findBoardById(id: UUID): Board {
        val dbDto = boardDBCrudRepository.findById(id)
            .orElseThrow { NoSuchElementException("Board with id $id not found") }
        return boardDBAdapter.toBoard(dbDto)
    }

    override fun deleteBoardById(id: UUID) {
        boardDBCrudRepository.deleteById(id)
    }

    override fun deleteTaskById(taskID: UUID) {
        boardDBCrudRepository.findAll().forEach { boardDBDto ->
            val updatedTasks = boardDBDto.tasks.filter { it.id != taskID }
            if (updatedTasks.size < boardDBDto.tasks.size) {    // FIXME: use separate repository
                boardDBCrudRepository.delete(boardDBDto)
                boardDBCrudRepository.save(boardDBDto.copy(tasks = updatedTasks))
            }
        }
    }
}

interface BoardDBCrudRepository : CrudRepository<BoardDBDto, UUID>