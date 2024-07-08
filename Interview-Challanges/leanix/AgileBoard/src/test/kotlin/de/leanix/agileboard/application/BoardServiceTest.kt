package de.leanix.agileboard.application

import de.leanix.agileboard.application.persistence.BoardRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.mockk.coEvery
import io.mockk.mockk

class BoardServiceTest : StringSpec({
    val boardRepository = mockk<BoardRepository>()
    val boardService = BoardService(boardRepository)

    "getAllBoards should return empty list when no boards exist" {
        coEvery { boardRepository.findAll() } returns emptyList()

        boardService.getAllBoards().shouldBeEmpty()
    }
})