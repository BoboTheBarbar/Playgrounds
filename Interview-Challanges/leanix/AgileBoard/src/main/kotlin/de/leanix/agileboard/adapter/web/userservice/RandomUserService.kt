package de.leanix.agileboard.adapter.web.userservice

import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.util.UUID

private val restTemplate = RestTemplate()

fun getUserData(userId: UUID): String = restTemplate.getForObject<String>("https://randomuser.me/api/")
