package com.jdcoding.todolist.feature_todo_list.domain.use_case

import com.jdcoding.todolist.feature_todo_list.domain.model.Task
import com.jdcoding.todolist.feature_todo_list.domain.repository.TodoListRepository

class DeleteTask(
    private val repository: TodoListRepository
) {

    suspend operator fun invoke(task: Task) {
        repository.deleteTask(task)
    }
}