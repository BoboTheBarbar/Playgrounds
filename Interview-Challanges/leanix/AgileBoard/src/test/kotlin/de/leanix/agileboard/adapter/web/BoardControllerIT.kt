package de.leanix.agileboard.adapter.web

import de.leanix.agileboard.BehaviorSpecIT
import de.leanix.agileboard.adapter.web.dto.BoardWebResponseDto
import de.leanix.agileboard.adapter.web.dto.CreateBoardWebRequestDTO
import io.kotest.matchers.shouldBe
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode


class BoardControllerIT() : BehaviorSpecIT() {
    init {
        Context("It is possible to query all boards") {
            Given("the server is up") {
                When("GET request is made to /boards") {
                    val result = restClient.exchange("$baseUrl/boards",
                        HttpMethod.GET,
                        null,
                        List::class.java)

                    Then("it should return 200 OK with an empty array") {
                        result.statusCode shouldBe HttpStatusCode.valueOf(200)
                        result.body?.size shouldBe 0
                    }
                }
            }
        }

        Context("It is possible to create a board") {
            Given("A request to create a board") {
                val boardWebRequestDto = CreateBoardWebRequestDTO("New Board")
//                val jsonBoard = Json.encodeToString(CreateBoardWebRequestDTO.serializer(), boardWebRequestDto)

                When("POST request is made to /boards") {
                    val result = restClient.exchange("$baseUrl/boards",
                        HttpMethod.POST,
                        HttpEntity(boardWebRequestDto),
                        BoardWebResponseDto::class.java)

                    Then("it should return 200 OK and return the created board") {
                        result.statusCode shouldBe HttpStatusCode.valueOf(201)
                        result.body?.name shouldBe boardWebRequestDto.name
                    }
                }
            }
        }
    }
}