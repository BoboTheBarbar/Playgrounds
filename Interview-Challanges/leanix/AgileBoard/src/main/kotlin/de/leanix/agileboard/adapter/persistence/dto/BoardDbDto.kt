package de.leanix.agileboard.adapter.persistence.dto

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.CascadeType
import jakarta.persistence.FetchType
import java.util.UUID

@Entity
data class BoardDbDto(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String? = null,
    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val tasks: List<TaskDbDto> = emptyList()
) {
    constructor() : this(UUID.randomUUID(), "", null, emptyList())
}