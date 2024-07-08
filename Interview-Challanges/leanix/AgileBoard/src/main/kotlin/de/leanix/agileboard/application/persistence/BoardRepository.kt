package de.leanix.agileboard.application.persistence

import de.leanix.agileboard.adapter.persistence.dto.BoardH2Dto
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface BoardRepository : CrudRepository<BoardH2Dto, UUID>