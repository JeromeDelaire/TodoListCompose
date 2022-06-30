package com.jdcoding.todolist.feature_todo_list.domain.use_case

import com.jdcoding.todolist.R
import com.jdcoding.todolist.core.util.UIText
import com.jdcoding.todolist.feature_todo_list.domain.model.Task
import com.jdcoding.todolist.feature_todo_list.domain.repository.TodoListRepository

class AddTask(
    private val repository: TodoListRepository
) {

    suspend operator fun invoke(task: Task): Result {
        return if(task.name.isBlank()) {
            Result.Error(
                UIText.StringResource(R.string.error_task_name_blank)
            )
        } else if (task.category.isBlank()) {
            Result.Error(
                UIText.StringResource(R.string.error_no_category)
            )
        } else {
            repository.insertTask(task)
            Result.Success
        }
    }

    sealed class Result {
        object Success: Result()
        data class Error(
            val message: UIText
        ): Result()
    }
}