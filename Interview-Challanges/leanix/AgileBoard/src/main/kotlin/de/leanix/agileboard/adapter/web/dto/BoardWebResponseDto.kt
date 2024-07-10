package de.leanix.agileboard.adapter.web.dto

import Board
import java.util.*

data class BoardWebResponseDto(
    val id: UUID,
    val name: String,
    val description: String? = null,
) {
    constructor(board: Board) : this(board.id, board.name, board.description)
}