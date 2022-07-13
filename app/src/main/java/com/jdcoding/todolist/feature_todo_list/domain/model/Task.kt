package com.jdcoding.todolist.feature_todo_list.domain.model

data class Task(
    val name: String,
    val completed: Boolean = false,
    val created: Long = System.currentTimeMillis(),
    val deadline: Long? = null,
    val isImportant: Boolean = false,
    val category: String,
    val id: Int? = null
)
