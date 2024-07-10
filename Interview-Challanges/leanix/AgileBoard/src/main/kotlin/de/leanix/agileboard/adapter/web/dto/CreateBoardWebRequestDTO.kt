package de.leanix.agileboard.adapter.web.dto

import Board
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateBoardWebRequestDTO(
    val name: String,
    val description: String? = null,
)

fun CreateBoardWebRequestDTO.toBoard() : Board {
    return Board(UUID.randomUUID(), name, description)
}