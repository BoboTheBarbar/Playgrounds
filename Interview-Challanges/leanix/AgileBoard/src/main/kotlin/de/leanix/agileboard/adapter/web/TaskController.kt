package de.leanix.agileboard.adapter.web

import de.leanix.agileboard.adapter.web.dto.TaskWebResponseDTO
import de.leanix.agileboard.adapter.web.dto.UpdateTaskWebRequestDTO
import de.leanix.agileboard.adapter.web.dto.toTask
import de.leanix.agileboard.application.TaskPatchDTO
import de.leanix.agileboard.application.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/tasks")
class TaskController(private val taskService: TaskService) {
    @PutMapping("/{id}")
    fun updateTask(
        @PathVariable id: UUID,
        @RequestBody updateTaskWebRequestDTO: UpdateTaskWebRequestDTO
    ): ResponseEntity<TaskWebResponseDTO> {
        val updatedTask = taskService.updateTask(updateTaskWebRequestDTO.toTask(id))
        val taskWebResponseDto = TaskWebResponseDTO(updatedTask)
        return ResponseEntity(taskWebResponseDto, HttpStatus.OK)
    }

    @PatchMapping("/{id}")
    fun updateTaskPartially(
        @PathVariable id: UUID,
        @RequestBody taskPatchDTO: TaskPatchDTO
    ): ResponseEntity<TaskWebResponseDTO> {
        val updatedTask = taskService.updateTaskPartially(id, taskPatchDTO)
        return ResponseEntity.ok(TaskWebResponseDTO(updatedTask))
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: UUID): ResponseEntity<Void> {
        taskService.deleteTaskById(id)
        return ResponseEntity.noContent().build()
    }
}