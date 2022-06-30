package com.jdcoding.todolist.feature_todo_list.domain.use_case

import com.jdcoding.todolist.feature_todo_list.domain.model.Task
import com.jdcoding.todolist.feature_todo_list.domain.repository.TodoListRepository
import com.jdcoding.todolist.feature_todo_list.domain.util.TaskOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasks(
    private val repository: TodoListRepository
) {

    operator fun invoke(
        taskOrder: TaskOrder,
        categoryName: String?
    ): Flow<List<Task>> {
        return when(taskOrder) {
            is TaskOrder.Name -> {
                repository.getTasks(categoryName).map { tasks ->
                    tasks.sortedWith(
                        compareBy(Task::isImportant, Task::name)
                    )
                }
            }
            is TaskOrder.Date -> {
                repository.getTasks(categoryName).map { tasks ->
                    tasks.sortedWith(
                        compareBy(Task::isImportant, Task::created)
                    )
                }
            }
            is TaskOrder.Date -> {
                repository.getTasks(categoryName).map { tasks ->
                    tasks.sortedWith(
                        compareBy(Task::isImportant, Task::deadline)
                    )
                }
            }
            else -> repository.getTasks(categoryName)
        }
    }
}