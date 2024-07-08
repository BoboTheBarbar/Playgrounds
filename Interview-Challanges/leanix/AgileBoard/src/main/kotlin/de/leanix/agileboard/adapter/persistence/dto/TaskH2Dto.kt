package de.leanix.agileboard.adapter.persistence.dto

import jakarta.persistence.*
import java.util.UUID

@Entity
data class TaskH2Dto(
    @Id
    val id: UUID,
    val name: String,
    val description: String? = null,
    @ManyToOne
    @JoinColumn(name = "board_id")
    val board: BoardH2Dto,
    @Column(name = "user_id") // Renamed column to avoid using reserved keyword
    val user: UUID,
    val status: Status? = null
) {
    constructor() : this(UUID.randomUUID(), "", null, BoardH2Dto(), UUID.randomUUID(), null)

    enum class Status {
        CREATED,
        STARTED,
        COMPLETED
    }
}