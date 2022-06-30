package com.jdcoding.todolist.feature_todo_list.domain.util

sealed class TaskOrder {
    object Date: TaskOrder()
    object Name: TaskOrder()
    object Deadline: TaskOrder()
}
