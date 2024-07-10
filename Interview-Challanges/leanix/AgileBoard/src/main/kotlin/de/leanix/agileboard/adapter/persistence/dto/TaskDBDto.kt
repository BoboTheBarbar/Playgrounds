package de.leanix.agileboard.adapter.persistence.dto

import jakarta.persistence.*
import java.util.*

@Entity
data class TaskDBDto(
    @Id
    val id: UUID,
    val name: String,
    val description: String? = null,
    @ManyToOne
    @JoinColumn(name = "board_id")
    val board: BoardDBDto,
    @Column(name = "user_id") // Renamed column to avoid using reserved keyword
    val user: UUID,
    val status: StatusDbDto? = null
) {
    constructor() : this(UUID.randomUUID(), "", null, BoardDBDto(), UUID.randomUUID(), null)

    enum class StatusDbDto {
        CREATED,
        STARTED,
        COMPLETED
    }
}