package com.jdcoding.todolist.feature_todo_list.presentation.todo_list

import com.jdcoding.todolist.feature_todo_list.domain.util.OrderType
import com.jdcoding.todolist.feature_todo_list.domain.util.TaskOrder
import com.jdcoding.todolist.feature_todo_list.presentation.todo_list.components.TaskType

data class TodoListState(
    val selectionMode: Boolean = false,
    val orderSectionVisible: Boolean = false,
    val category: String? = null,
    val taskOrder: TaskOrder = TaskOrder.Name(OrderType.Ascending),
    val tasks: List<TaskState> = emptyList(),
    val categories: List<String> = emptyList(),
    val taskType: TaskType = TaskType.Todo,
    val animationEnabled: Boolean = true,
    val showAddTaskValidate: Boolean = false,
    val quickAddTaskName: String = "",
    val searchQuery: String = "",
    val searchMode: Boolean = false
)
