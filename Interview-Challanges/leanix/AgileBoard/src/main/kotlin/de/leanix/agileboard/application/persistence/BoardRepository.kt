package de.leanix.agileboard.application.persistence

import de.leanix.agileboard.adapter.persistence.dto.BoardDbDto
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface BoardRepository : CrudRepository<BoardDbDto, UUID>