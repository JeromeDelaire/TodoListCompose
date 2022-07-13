package com.jdcoding.todolist.feature_todo_list.data.local

import com.jdcoding.todolist.feature_todo_list.domain.model.Task
import com.jdcoding.todolist.feature_todo_list.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat

class FakeTodoListRepositoryImpl: TodoListRepository {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    private val today = dateFormat.parse("01/07/2022")!!.time
    private val yesterday = dateFormat.parse("30/06/2022")!!.time
    private val defaultTasks = mutableListOf(
        Task(
            name = "B",
            created = yesterday,
            deadline = today,
            category = "Shopping",
            isImportant = false,
            id = 1
        ),
        Task(
            name = "A",
            created = today,
            deadline = yesterday,
            category = "Work",
            isImportant = false,
            id = 2
        ),
        Task(
            name = "D",
            created = today,
            deadline = yesterday,
            category = "Shopping",
            isImportant = true,
            id = 3
        ),
        Task(
            name = "C",
            created = yesterday,
            deadline = today,
            category = "Work",
            isImportant = true,
            id = 4
        )
    )

    private val categories  = mutableListOf<String>()
    private val observableCategories = MutableStateFlow<List<String>>(categories)

    private val tasks = defaultTasks
    private val observableTasks = MutableStateFlow<List<Task>>(defaultTasks)

    override suspend fun insertCategory(categoryName: String) {
        categories.add(categoryName)
        observableCategories.emit(categories)
    }

    override suspend fun deleteCategory(categoryName: String) {
        categories.remove(categoryName)
        observableCategories.emit(categories)
    }

    override fun getCategories(): Flow<List<String>> {
        return observableCategories
    }

    override suspend fun insertTask(task: Task) {
        tasks.add(task)
        observableTasks.emit(tasks)
    }

    override suspend fun deleteTask(task: Task) {
        tasks.remove(task)
        observableTasks.emit(tasks)
    }

    override fun getTasks(category: String?): Flow<List<Task>> {
        return if(category == null) {
            observableTasks
        } else {
            flow {
                emit(tasks.filter { it.category == category })
            }
        }
    }
}