package de.leanix.agileboard.application

import java.util.*

data class TaskPatchDTO(
    val name: String?,
    val description: String?,
    val status: String?,
    val userId: UUID?
)