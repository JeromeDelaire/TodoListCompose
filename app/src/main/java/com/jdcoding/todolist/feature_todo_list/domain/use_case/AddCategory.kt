package com.jdcoding.todolist.feature_todo_list.domain.use_case

import com.jdcoding.todolist.R
import com.jdcoding.todolist.core.util.UIText
import com.jdcoding.todolist.feature_todo_list.domain.repository.TodoListRepository

class AddCategory(
    private val repository: TodoListRepository
) {

    suspend operator fun invoke(categoryName: String): Result {
        return if(categoryName.isBlank()) {
            Result.Error(
                UIText.StringResource(R.string.error_category_name_blank)
            )
        } else {
            repository.insertCategory(categoryName)
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