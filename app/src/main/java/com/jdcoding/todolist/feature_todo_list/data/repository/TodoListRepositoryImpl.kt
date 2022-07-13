package com.jdcoding.todolist.feature_todo_list.data.repository

import com.jdcoding.todolist.feature_todo_list.data.local.CategoryDao
import com.jdcoding.todolist.feature_todo_list.data.local.TaskDao
import com.jdcoding.todolist.feature_todo_list.data.local.entity.CategoryEntity
import com.jdcoding.todolist.feature_todo_list.data.mapper.toTask
import com.jdcoding.todolist.feature_todo_list.data.mapper.toTaskEntity
import com.jdcoding.todolist.feature_todo_list.domain.model.Task
import com.jdcoding.todolist.feature_todo_list.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoListRepositoryImpl(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
): TodoListRepository {

    override suspend fun insertCategory(categoryName: String) {
        categoryDao.insertCategory(CategoryEntity(categoryName))
    }

    override suspend fun deleteCategory(categoryName: String) {
        categoryDao.deleteCategory(CategoryEntity(categoryName))
    }

    override fun getCategories(): Flow<List<String>> {
        return categoryDao.getCategories().map { entities ->
            entities.map {
                it.name
            }
        }
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toTaskEntity())
    }

    override fun getTasks(category: String?, searchQuery: String): Flow<List<Task>> {
        return taskDao.getTasks(category, searchQuery).map { entities ->
            entities.map {
                it.toTask()
            }
        }
    }

    override suspend fun getTask(id: Int): Task {
        return taskDao.getTask(id)
    }
}