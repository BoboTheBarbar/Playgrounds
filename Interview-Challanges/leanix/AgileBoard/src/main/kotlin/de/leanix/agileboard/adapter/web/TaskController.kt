package de.leanix.agileboard.adapter.web

import de.leanix.agileboard.adapter.web.dto.TaskWebResponseDTO
import de.leanix.agileboard.adapter.web.dto.UpdateTaskWebRequestDTO
import de.leanix.agileboard.adapter.web.dto.toTask
import de.leanix.agileboard.application.BoardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/tasks")
class TaskController(private val boardService: BoardService) {
    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: UUID, @RequestBody updateTaskWebRequestDTO: UpdateTaskWebRequestDTO): ResponseEntity<TaskWebResponseDTO> {
        return try {
            val updatedTask = boardService.updateTask(updateTaskWebRequestDTO.toTask(id))
            val taskWebResponseDto = TaskWebResponseDTO(updatedTask)
            ResponseEntity(taskWebResponseDto, HttpStatus.OK)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
}