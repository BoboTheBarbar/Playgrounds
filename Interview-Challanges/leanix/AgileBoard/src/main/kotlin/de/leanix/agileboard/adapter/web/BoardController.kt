package de.leanix.agileboard.application

import Board
import de.leanix.agileboard.adapter.web.dto.*
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
        return try {
            val board = boardService.getBoard(id)
            val boardWebResponseDto = BoardWebResponseDto(board)
            ResponseEntity.ok(boardWebResponseDto)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteBoard(@PathVariable id: UUID): ResponseEntity<Unit> {
        return try {
            boardService.deleteBoard(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/{id}/tasks")
    fun addTaskToBoard(@PathVariable id: UUID, @RequestBody createTaskRequestDto: CreateTaskWebRequestDTO): ResponseEntity<TaskWebResponseDTO> {
        return try {
            val addedTask = boardService.addTaskToBoard(createTaskRequestDto.toTask(), id)
            val taskWebResponseDto = TaskWebResponseDTO(addedTask)
            ResponseEntity(taskWebResponseDto, HttpStatus.CREATED)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}
