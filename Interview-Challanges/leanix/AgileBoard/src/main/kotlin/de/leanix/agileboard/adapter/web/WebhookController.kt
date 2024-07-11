package de.leanix.agileboard.adapter.web

import de.leanix.agileboard.application.TaskService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class WebhookController(val taskService: TaskService) {

    private val logger = LoggerFactory.getLogger(WebhookController::class.java)

    @PostMapping("/webhooks/user-deleted")
    fun handleUserDeletion(@RequestBody payload: WebhookPayload): ResponseEntity<Void> {
        return try {
            val userId = payload.data.user
            taskService.deleteTasksByUserId(userId)
            ResponseEntity.ok().build()
        } catch (e: Exception) {
            logger.error("Error processing user deletion webhook", e)
            ResponseEntity.accepted().build()
        }

    }
}

data class WebhookPayload(
    val time: String,
    val data: UserData
)

data class UserData(
    val user: UUID
)