package de.leanix.agileboard.adapter.web.dto

import java.util.UUID

data class TaskWebResponseDTO(
    val id: UUID,
    val name: String,
    val description: String?,
    val userId: UUID,
    val status: String?
) {
    constructor(task: Board.Task) : this(task.id, task.name, task.description, task.user, task.status?.name)
}