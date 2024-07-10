package de.leanix.agileboard.adapter.web

import de.leanix.agileboard.BehaviorSpecIT
import de.leanix.agileboard.adapter.web.dto.BoardWebResponseDto
import de.leanix.agileboard.adapter.web.dto.CreateBoardWebRequestDTO
import de.leanix.agileboard.adapter.web.dto.CreateTaskWebRequestDTO
import de.leanix.agileboard.adapter.web.dto.TaskWebResponseDTO
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode
import java.util.*


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
        }

        Context("It is possible to query a single board") {
            Given("A board exists in the system") {
                val createdBoard = addBoard()

                When("I request the board at /boards/{id}") {
                    val result = getBoardRequestById<BoardWebResponseDto>(createdBoard.id)

                    Then("it should return OK and the stored board") {
                        result.statusCode shouldBe HttpStatusCode.valueOf(200)
                        result.body shouldBe createdBoard
                    }
                }
            }

            Given("A board does not exist in the system") {
                val fakeBoardId = UUID.randomUUID()
                When("I request the board at /boards/{id}") {
                    val result = getBoardRequestById<String>(fakeBoardId)

                    Then("it should return NOT_FOUND") {
                        result.statusCode shouldBe HttpStatusCode.valueOf(404)
                    }
                }
            }
        }

        Context("It is possible to delete a specific board") {
            Given("A board exists in the system") {
                val createdBoard = addBoard()

                When("I delete the board at /boards/{id}") {
                    val result = restClient.exchange<Unit>(
                        "$baseUrl/boards/${createdBoard.id}",
                        HttpMethod.DELETE,
                        null
                    )

                    Then("it should return NO_CONTENT") {
                        result.statusCode shouldBe HttpStatusCode.valueOf(204)

                    }

                    And("the board should no longer exist") {
                        val responseEntity = getBoardRequestById<String>(createdBoard.id)

                        responseEntity.statusCode shouldBe HttpStatusCode.valueOf(404)
                    }
                }
            }
        }

        Context("It is possible to add a Task to a Board") {
            Given("A board exists in the system") {
                val createdBoard = addBoard()


                When("I add a task to the board at /boards/{id}/tasks") {
                    val taskWebRequestDTO =
                        CreateTaskWebRequestDTO(name = "New Task", description = null, userId = UUID.randomUUID())
                    val result = restClient.exchange<TaskWebResponseDTO>(
                        "$baseUrl/boards/${createdBoard.id}/tasks",
                        HttpMethod.POST,
                        HttpEntity(taskWebRequestDTO)
                    )

                    Then("it should return CREATED and the stored task") {
                        result.statusCode shouldBe HttpStatusCode.valueOf(201)
                        result.body?.name shouldBe taskWebRequestDTO.name
                    }
                }
            }

            Given("A board does not exist in the system") {
                val fakeBoardId = UUID.randomUUID()
                val taskWebRequestDTO =
                    CreateTaskWebRequestDTO(name = "New Task", description = null, userId = UUID.randomUUID())

                When("I add a task to the fake board at /boards/{id}/tasks") {
                    val result = restClient.exchange<String>(
                        "$baseUrl/boards/$fakeBoardId/tasks",
                        HttpMethod.POST,
                        HttpEntity(taskWebRequestDTO)
                    )

                    Then("it should return NOT_FOUND") {
                        result.statusCode shouldBe HttpStatusCode.valueOf(400)
                    }
                }
            }
        }
    }

}