package de.leanix.agileboard.application

import Board
import de.leanix.agileboard.adapter.web.dto.BoardWebResponseDto
import de.leanix.agileboard.adapter.web.dto.CreateBoardWebRequestDTO
import de.leanix.agileboard.adapter.web.dto.toBoard
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/boards")
class BoardController(private val boardService: BoardService) {

    @GetMapping
    fun getAllBoards(): ResponseEntity<List<Board>> {
        val boards = boardService.getAllBoards()
        return ResponseEntity.ok(boards)
    }

    @PostMapping
    fun createBoard(@RequestBody createBoardWebRequestDTO: CreateBoardWebRequestDTO): ResponseEntity<BoardWebResponseDto> {
        val board = boardService.addBoard(createBoardWebRequestDTO.toBoard())
        val boardWebResponseDto = BoardWebResponseDto(board)
        return ResponseEntity(boardWebResponseDto, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getBoard(@PathVariable id: UUID): ResponseEntity<BoardWebResponseDto> {
        val board = boardService.getBoard(id)
        val boardWebResponseDto = BoardWebResponseDto(board)
        return ResponseEntity.ok(boardWebResponseDto)
    }
}
