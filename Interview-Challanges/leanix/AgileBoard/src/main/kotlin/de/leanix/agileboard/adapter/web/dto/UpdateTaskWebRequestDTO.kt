package de.leanix.agileboard.adapter.web.dto

import java.util.*

data class UpdateTaskWebRequestDTO(
    val name: String,
    val description: String?,
    val userId: UUID,
    val status: String
)

fun UpdateTaskWebRequestDTO.toTask(id: UUID) = Board.Task(
    id,
    name,
    description,
    userId,
    Board.Status.valueOf(status)
)