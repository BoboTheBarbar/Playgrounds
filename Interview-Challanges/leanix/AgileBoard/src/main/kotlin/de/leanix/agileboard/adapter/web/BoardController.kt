package de.leanix.agileboard.adapter.web

import Board
import de.leanix.agileboard.adapter.web.dto.*
import de.leanix.agileboard.adapter.web.userservice.getUserData
import de.leanix.agileboard.application.BoardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

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

        boardWebResponseDto.tasks.forEach {
            it.userData = getUserData(userId = it.userId)
        }

        return ResponseEntity.ok(boardWebResponseDto)
    }

    @DeleteMapping("/{id}")
    fun deleteBoard(@PathVariable id: UUID): ResponseEntity<Unit> {
        boardService.deleteBoard(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/tasks")
    fun addTaskToBoard(
        @PathVariable id: UUID,
        @RequestBody createTaskRequestDto: CreateTaskWebRequestDTO
    ): ResponseEntity<TaskWebResponseDTO> {
        val addedTask = boardService.addTaskToBoard(createTaskRequestDto.toTask(), id)
        val taskWebResponseDto = TaskWebResponseDTO(addedTask)
        return ResponseEntity(taskWebResponseDto, HttpStatus.CREATED)
    }
}
