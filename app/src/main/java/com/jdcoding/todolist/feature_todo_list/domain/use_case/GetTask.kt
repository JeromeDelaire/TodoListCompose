package com.jdcoding.todolist.feature_todo_list.domain.use_case

import com.jdcoding.todolist.feature_todo_list.domain.model.Task
import com.jdcoding.todolist.feature_todo_list.domain.repository.TodoListRepository
import com.jdcoding.todolist.feature_todo_list.domain.util.OrderType
import com.jdcoding.todolist.feature_todo_list.domain.util.TaskOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTask(
    private val repository: TodoListRepository
) {

    suspend operator fun invoke(id: Int): Task {
        return repository.getTask(id)
    }
}