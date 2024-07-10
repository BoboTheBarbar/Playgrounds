package de.leanix.agileboard.application

import Board
import de.leanix.agileboard.application.persistence.BoardRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class BoardServiceTest : BehaviorSpec({

    val boardRepository = mockk<BoardRepository>()
    val boardService = BoardService(boardRepository)

    Context("service should be able to return all available boards") {
        Given("no boards exist") {
            coEvery { boardRepository.findAllBoards() } returns emptyList()

            When("getAllBoards is called") {
                val result = boardService.getAllBoards()

                Then("it should return an empty list") {
                    result.shouldBeEmpty()
                }
            }
        }

        Given("boards exist") {
            val boards = listOf(Board(UUID.randomUUID(),"Board 1"), Board(UUID.randomUUID(),"Board 2"))
            coEvery { boardRepository.findAllBoards() } returns boards

            When("boards are requested") {
                val result = boardService.getAllBoards()

                Then("it should return all boards") {
                    result.size shouldBe boards.size
                }
            }
        }
    }

    Context("service should be able to create a new board") {
        Given("a board is created") {
            val board = createBoard()
            coEvery { boardRepository.saveBoard(board) } returns board

            When("createBoard is called") {
                val result = boardService.addBoard(board)

                Then("it should return the created board") {
                    result shouldBe board
                }
            }
        }
    }

    Context("service should be able to return a single board") {
        Given("a board exists") {
            val board = createBoard()
            coEvery { boardRepository.findBoardById(board.id) } returns board

            When("getBoard is called") {
                val result = boardService.getBoard(board.id)

                Then("it should return the board") {
                    result shouldBe board
                }
            }
        }
    }

    Context("service should be able to delete a specific board") {
        Given("a board exists") {
            val board = createBoard()
            justRun { boardRepository.deleteBoardById(board.id) }

            When("deleteBoard is called") {
                boardService.deleteBoard(board.id)

                Then("it should delete the board") {
                    verify { boardRepository.deleteBoardById(board.id) }
                }
            }
        }
    }

    // TODO: Add test for non-existing board deletion and retrieval requests
})

private fun createBoard(): Board {
    val board = Board(UUID.randomUUID(), "Board 1")
    return board
}