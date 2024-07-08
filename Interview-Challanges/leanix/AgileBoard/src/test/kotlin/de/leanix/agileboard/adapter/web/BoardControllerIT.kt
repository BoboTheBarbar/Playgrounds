package de.leanix.agileboard.adapter.web

import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerIT(@Autowired val mockMvc: MockMvc) : StringSpec({

    extension(SpringExtension)

    "GET /boards should return 200 OK" {
        mockMvc.perform(get("/boards"))
            .andExpect(status().isOk)
            .andReturn().response.contentAsString shouldBe "[]"
    }
})
