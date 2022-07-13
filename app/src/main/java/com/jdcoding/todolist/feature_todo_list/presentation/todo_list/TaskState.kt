package com.jdcoding.todolist.feature_todo_list.presentation.todo_list

import com.jdcoding.todolist.feature_todo_list.domain.model.Task
import com.jdcoding.todolist.feature_todo_list.presentation.todo_list.components.TaskType

data class TaskState(
    val task: Task,
    val selected: Boolean = false
)
