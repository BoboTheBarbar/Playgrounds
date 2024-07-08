package de.leanix.agileboard.application

import de.leanix.agileboard.application.persistence.BoardRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.mockk.coEvery
import io.mockk.mockk

class BoardServiceTest : BehaviorSpec({
    Context("getAllBoards Endpoint should return all available boards") {
        Given("no boards exist") {
            val boardRepository = mockk<BoardRepository>()
            val boardService = BoardService(boardRepository)
            coEvery { boardRepository.findAll() } returns emptyList()

            When("getAllBoards is called") {
                val result = boardService.getAllBoards()

                Then("it should return an empty list") {
                    result.shouldBeEmpty()
                }
            }
        }
    }
})