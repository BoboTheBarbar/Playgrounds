package de.leanix.agileboard.adapter.web.dto

import java.util.*

data class CreateTaskWebRequestDTO(
    val name: String,
    val description: String?,
    val userId: UUID
)

fun CreateTaskWebRequestDTO.toTask() = Board.Task(
    UUID.randomUUID(),
    name,
    description,
    userId,
    Board.Status.CREATED
)
