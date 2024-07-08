package de.leanix.agileboard.adapter.persistence.dto

import jakarta.persistence.*
import java.util.UUID

@Entity
data class TaskDbDto(
    @Id
    val id: UUID,
    val name: String,
    val description: String? = null,
    @ManyToOne
    @JoinColumn(name = "board_id")
    val board: BoardDbDto,
    @Column(name = "user_id") // Renamed column to avoid using reserved keyword
    val user: UUID,
    val status: StatusDbDto? = null
) {
    constructor() : this(UUID.randomUUID(), "", null, BoardDbDto(), UUID.randomUUID(), null)

    enum class StatusDbDto {
        CREATED,
        STARTED,
        COMPLETED
    }
}