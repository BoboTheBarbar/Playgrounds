package de.leanix.agileboard

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort

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

}