package de.leanix.agileboard

import de.leanix.agileboard.adapter.web.dto.BoardWebResponseDto
import de.leanix.agileboard.adapter.web.dto.CreateBoardWebRequestDTO
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import java.util.*

/**
 * Base class for integration tests that require a running Spring Boot application.
 * The application will be started with a random port.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BehaviorSpecIT(body: BehaviorSpec.() -> Unit = {}) : BehaviorSpec(body) {

    override fun extensions() = listOf(SpringExtension)

    @LocalServerPort
    private var localServerPort: Int = 0

    val restClient = TestRestTemplate()

    val baseUrl: String
        get() = "http://localhost:$localServerPort"

    final inline fun <reified T> getBoardRequestById(fakeBoardId: UUID?): ResponseEntity<T> {
        val result = restClient.exchange(
            "$baseUrl/boards/$fakeBoardId",
            HttpMethod.GET,
            null,
            T::class.java
        )
        return result
    }

    fun addBoard(): BoardWebResponseDto {
        val boardWebRequestDto = CreateBoardWebRequestDTO("New Board")
        val createdBoard =
            postBoardCreationRequest(boardWebRequestDto).body ?: throw IllegalStateException("Board creation failed")
        return createdBoard
    }

    fun postBoardCreationRequest(boardWebRequestDto: CreateBoardWebRequestDTO): ResponseEntity<BoardWebResponseDto> =
        restClient.exchange(
            "$baseUrl/boards",
            HttpMethod.POST,
            HttpEntity(boardWebRequestDto),
            BoardWebResponseDto::class.java
        )

}