package de.leanix.agileboard.adapter.persistence.dto

import Board

fun BoardH2Dto.toBoard(): Board {
    return Board(
        id = id,
        name = name,
        description = description,
        tasks = tasks.map { it.toTask() }
    )
}

fun TaskH2Dto.toTask(): Board.Task {
    return Board.Task(
        id = id,
        name = name,
        description = description,
        user = user,
        status = status?.toTaskStatus()
    )
}

fun TaskH2Dto.Status.toTaskStatus(): Board.Status {
    return when (this) {
        TaskH2Dto.Status.CREATED -> Board.Status.CREATED
        TaskH2Dto.Status.STARTED -> Board.Status.STARTED
        TaskH2Dto.Status.COMPLETED -> Board.Status.COMPLETED
    }
}