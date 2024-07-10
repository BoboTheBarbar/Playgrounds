package de.leanix.agileboard.adapter.persistence.dto

import Board
import org.springframework.stereotype.Component

@Component
class BoardDBAdapter (val taskDBAdapter: TaskDBAdapter = TaskDBAdapter()) {
    fun toBoard(boardDbDto: BoardDBDto): Board = Board(
        id = boardDbDto.id,
        name = boardDbDto.name,
        description = boardDbDto.description,
        tasks = boardDbDto.tasks.map { taskDBAdapter.toTask(it) }
    )

    fun toBoardDBDto(board: Board): BoardDBDto {
        val tasks = mutableListOf<TaskDBDto>()

        val boardAsDBDto = BoardDBDto(
            id = board.id,
            name = board.name,
            description = board.description,
            tasks = tasks
        )
        val tasksAsDBList = board.tasks.map { taskDBAdapter.toTaskDBDto(it, boardAsDBDto) }
        tasks.addAll(tasksAsDBList)

        return boardAsDBDto
    }
}

class TaskDBAdapter {
    fun toTask(taskDBDto: TaskDBDto): Board.Task = Board.Task(
        id = taskDBDto.id,
        name = taskDBDto.name,
        description = taskDBDto.description,
        user = taskDBDto.user,
        status = taskDBDto.status?.let { toTaskStatus(it) }
    )

    fun toTaskDBDto(task: Board.Task, boardDBDto: BoardDBDto): TaskDBDto = TaskDBDto(
        id = task.id,
        name = task.name,
        description = task.description,
        board = boardDBDto,
        user = task.user,
        status = task.status?.let { toStatusDBDto(it) }
    )

    fun toTaskStatus(taskDbStatusDbDto: TaskDBDto.StatusDbDto): Board.Status {
        return when (taskDbStatusDbDto) {
            TaskDBDto.StatusDbDto.CREATED -> Board.Status.CREATED
            TaskDBDto.StatusDbDto.STARTED -> Board.Status.STARTED
            TaskDBDto.StatusDbDto.COMPLETED -> Board.Status.COMPLETED
        }
    }

    fun toStatusDBDto(taskStatus: Board.Status): TaskDBDto.StatusDbDto {
        return when (taskStatus) {
            Board.Status.CREATED -> TaskDBDto.StatusDbDto.CREATED
            Board.Status.STARTED -> TaskDBDto.StatusDbDto.STARTED
            Board.Status.COMPLETED -> TaskDBDto.StatusDbDto.COMPLETED
        }
    }
}