package com.jdcoding.todolist.feature_todo_list.domain.use_case

import com.jdcoding.todolist.feature_todo_list.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow

class GetCategory(
    private val repository: TodoListRepository
) {

    operator fun invoke(): Flow<List<String>> {
        return repository.getCategories()
    }
}