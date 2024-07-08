package de.leanix.agileboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AgileBoardApplication

fun main(args: Array<String>) {
    runApplication<AgileBoardApplication>(*args)
}
