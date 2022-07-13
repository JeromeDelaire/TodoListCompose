package com.jdcoding.todolist.feature_todo_list.presentation.todo_list.components

sealed class TaskType {
    object Todo: TaskType()
    object Completed: TaskType()
}
