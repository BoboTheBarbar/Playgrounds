package de.leanix.agileboard.adapter.web

import de.leanix.agileboard.adapter.web.dto.TaskWebResponseDTO
import de.leanix.agileboard.adapter.web.dto.UpdateTaskWebRequestDTO
import de.leanix.agileboard.adapter.web.dto.toTask
import de.leanix.agileboard.application.TaskService
import de.leanix.agileboard.application.TaskPatchDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/tasks")
class TaskController(private val taskService: TaskService) {
    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: UUID, @RequestBody updateTaskWebRequestDTO: UpdateTaskWebRequestDTO): ResponseEntity<TaskWebResponseDTO> {
        return try {
            val updatedTask = taskService.updateTask(updateTaskWebRequestDTO.toTask(id))
            val taskWebResponseDto = TaskWebResponseDTO(updatedTask)
            ResponseEntity(taskWebResponseDto, HttpStatus.OK)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @PatchMapping("/{id}")
    fun updateTaskPartially(@PathVariable id: UUID, @RequestBody taskPatchDTO: TaskPatchDTO): ResponseEntity<TaskWebResponseDTO> {
        return try {
            val updatedTask = taskService.updateTaskPartially(id, taskPatchDTO)
            ResponseEntity.ok(TaskWebResponseDTO(updatedTask))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: UUID): ResponseEntity<Void> {
        taskService.deleteTaskById(id)
        return ResponseEntity.noContent().build()
    }
}