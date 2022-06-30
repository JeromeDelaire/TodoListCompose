package com.jdcoding.todolist.feature_todo_list.domain.repository

import com.jdcoding.todolist.feature_todo_list.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TodoListRepository {

    suspend fun insertCategory(categoryName: String)

    suspend fun deleteCategory(categoryName: String)

    fun getCategories(): Flow<List<String>>

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    fun getTasks(category: String?) : Flow<List<Task>>
}