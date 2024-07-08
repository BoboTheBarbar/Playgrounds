package de.leanix.agileboard.adapter.persistence.dto

import Board

fun BoardDbDto.toBoard(): Board {
    return Board(
        id = id,
        name = name,
        description = description,
        tasks = tasks.map { it.toTask() }
    )
}

fun TaskDbDto.toTask(): Board.Task {
    return Board.Task(
        id = id,
        name = name,
        description = description,
        user = user,
        status = status?.toTaskStatus()
    )
}

fun TaskDbDto.StatusDbDto.toTaskStatus(): Board.Status {
    return when (this) {
        TaskDbDto.StatusDbDto.CREATED -> Board.Status.CREATED
        TaskDbDto.StatusDbDto.STARTED -> Board.Status.STARTED
        TaskDbDto.StatusDbDto.COMPLETED -> Board.Status.COMPLETED
    }
}