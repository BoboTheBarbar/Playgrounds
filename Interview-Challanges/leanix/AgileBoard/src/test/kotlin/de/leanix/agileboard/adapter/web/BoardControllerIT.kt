package de.leanix.agileboard.adapter.web

import de.leanix.agileboard.BehaviorSpecIT
import de.leanix.agileboard.adapter.web.dto.BoardWebResponseDto
import de.leanix.agileboard.adapter.web.dto.CreateBoardWebRequestDTO
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity


class BoardControllerIT() : BehaviorSpecIT() {
    init {
        Context("It is possible to query all boards") {
            Given("No prior boards exist in the system") {
                When("I request all boards at /boards") {
                    val result = restClient.exchange(
                        "$baseUrl/boards",
                        HttpMethod.GET,
                        null,
                        List::class.java
                    )

                    Then("it should return OK with an empty array") {
                        result.statusCode shouldBe HttpStatusCode.valueOf(200)
                        result.body?.size shouldBe 0
                    }
                }
            }
        }

        Context("It is possible to create a board") {
            Given("A request to create a board") {
                val boardWebRequestDto = CreateBoardWebRequestDTO("New Board")

                When("I post the new board at /boards") {
                    val result = postBoardCreationRequest(boardWebRequestDto)

                    Then("it should return CREATED and the stored board") {
                        result.statusCode shouldBe HttpStatusCode.valueOf(201)
                        result.body?.name shouldBe boardWebRequestDto.name
                    }
                }
            }

            // TODO: add error cases
        }

        Context("It is possible to query a single board") {
            Given("A board exists in the system") {
                val createdBoard = createBoard()

                When("I request the board at /boards/{id}") {
                    val result = restClient.exchange(
                        "$baseUrl/boards/${createdBoard.id}",
                        HttpMethod.GET,
                        null,
                        BoardWebResponseDto::class.java
                    )

                    Then("it should return OK and the stored board") {
                        result.statusCode shouldBe HttpStatusCode.valueOf(200)
                        result.body shouldBe createdBoard
                    }
                }
            }
        }

        Context("It is possible to delete a specific board") {
            Given("A board exists in the system") {
                val createdBoard = createBoard()

                When("I delete the board at /boards/{id}") {
                    val result = restClient.exchange<Unit>(
                        "$baseUrl/boards/${createdBoard.id}",
                        HttpMethod.DELETE,
                        null
                    )

                    Then("it should return NO_CONTENT") {
                        result.statusCode shouldBe HttpStatusCode.valueOf(204)

                    }

//                    And("the board should no longer exist") { // TODO: add simple Exception handler
//                        val result = restClient.exchange(
//                            "$baseUrl/boards/${createdBoard.id}",
//                            HttpMethod.GET,
//                            null,
//                            BoardWebResponseDto::class.java
//                        )
//                        result.statusCode shouldBe HttpStatusCode.valueOf(404)
//                    }
                }
            }
        }
    }

    private fun createBoard(): BoardWebResponseDto {
        val boardWebRequestDto = CreateBoardWebRequestDTO("New Board")
        val createdBoard =
            postBoardCreationRequest(boardWebRequestDto).body ?: throw IllegalStateException("Board creation failed")
        return createdBoard
    }

    private fun postBoardCreationRequest(boardWebRequestDto: CreateBoardWebRequestDTO): ResponseEntity<BoardWebResponseDto> =
        restClient.exchange(
            "$baseUrl/boards",
            HttpMethod.POST,
            HttpEntity(boardWebRequestDto),
            BoardWebResponseDto::class.java
        )
}