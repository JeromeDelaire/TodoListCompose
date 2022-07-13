package com.jdcoding.todolist.feature_todo_list.domain.use_case

import com.jdcoding.todolist.feature_todo_list.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategories(
    private val repository: TodoListRepository
) {

    operator fun invoke(): Flow<List<String>> {
        return repository.getCategories().map { categories ->
            categories.sortedBy {
                it
            }
        }
    }
}