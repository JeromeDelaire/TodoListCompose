package com.jdcoding.todolist.feature_todo_list.domain.use_case

import com.jdcoding.todolist.feature_todo_list.domain.model.Task
import com.jdcoding.todolist.feature_todo_list.domain.repository.TodoListRepository
import com.jdcoding.todolist.feature_todo_list.domain.util.OrderType
import com.jdcoding.todolist.feature_todo_list.domain.util.TaskOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasks(
    private val repository: TodoListRepository
) {

    operator fun invoke(
        taskOrder: TaskOrder,
        categoryName: String? = null,
        searchQuery: String = ""
    ): Flow<List<Task>> {
        return when(taskOrder) {
            is TaskOrder.Name -> {
                repository.getTasks(categoryName, searchQuery).map { tasks ->
                    tasks.sortedWith(
                        when(taskOrder.orderType) {
                            is OrderType.Ascending -> {
                                compareBy<Task> { !it.isImportant} .thenBy { it.name }
                            }

                            is OrderType.Descending -> {
                                compareBy<Task> { !it.isImportant} .thenByDescending { it.name }
                            }
                        }
                    )
                }
            }
            is TaskOrder.Date -> {
                repository.getTasks(categoryName, searchQuery).map { tasks ->
                    tasks.sortedWith(
                        when(taskOrder.orderType) {
                            is OrderType.Ascending -> {
                                compareBy<Task> { !it.isImportant} .thenBy { it.created }
                            }

                            is OrderType.Descending -> {
                                compareBy<Task> { !it.isImportant} .thenByDescending { it.created }
                            }
                        }
                    )
                }
            }
            is TaskOrder.Deadline -> {
                repository.getTasks(categoryName, searchQuery).map { tasks ->
                    tasks.sortedWith(
                        when(taskOrder.orderType) {
                            is OrderType.Ascending -> {
                                compareBy<Task> { !it.isImportant} .thenBy { it.deadline }
                            }

                            is OrderType.Descending -> {
                                compareBy<Task> { !it.isImportant} .thenByDescending { it.deadline }
                            }
                        }
                    )
                }
            }
        }/*.map { tasks ->
            tasks.filter {
                !it.completed
            }
        }*/
    }
}