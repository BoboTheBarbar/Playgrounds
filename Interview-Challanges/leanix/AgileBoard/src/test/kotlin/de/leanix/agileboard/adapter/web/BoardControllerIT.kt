package de.leanix.agileboard.adapter.web

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerIT(@Autowired val mockMvc: MockMvc) : BehaviorSpec({

    extension(SpringExtension)

    Given("the server is up") {
        When("GET request is made to /boards") {
            val result = mockMvc.perform(get("/boards"))
                .andReturn().response

            Then("it should return 200 OK with an empty array") {
                result.status shouldBe 200
                result.contentAsString shouldBe "[]"
            }
        }
    }
})