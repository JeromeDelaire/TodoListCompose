package com.jdcoding.todolist.feature_todo_list.data.mapper

import com.jdcoding.todolist.feature_todo_list.data.local.entity.TaskEntity
import com.jdcoding.todolist.feature_todo_list.domain.model.Task

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        name = name,
        completed = completed,
        created = created,
        deadline = deadline,
        isImportant = isImportant,
        category = category,
        id = id
    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        name = name,
        completed = completed,
        created = created,
        deadline = deadline,
        isImportant = isImportant,
        category = category,
        id = id
    )
}