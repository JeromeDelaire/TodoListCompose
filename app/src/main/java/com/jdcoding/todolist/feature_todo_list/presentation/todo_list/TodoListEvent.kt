package com.jdcoding.todolist.feature_todo_list.presentation.todo_list

import com.jdcoding.todolist.feature_todo_list.domain.util.TaskOrder

sealed class TodoListEvent {
    data class OnTaskClicked(val task: TaskState): TodoListEvent()
    data class OnTaskLongClicked(val task: TaskState): TodoListEvent()
    object OnAddTaskClicked: TodoListEvent()
    data class OnCategorySelected(val categoryName: String?): TodoListEvent()
    data class OnTaskCompletionChanged(val task: TaskState): TodoListEvent()
    data class OnTaskOrderChanged(val taskOrder: TaskOrder): TodoListEvent()
    object OnToggleOrderSection: TodoListEvent()
    object ShowCompletedTasks: TodoListEvent()
    object DeleteSelectedTasks: TodoListEvent()
    data class AddCategory(val categoryName: String): TodoListEvent()
    data class OnQuickAddingTaskChanged(val taskName: String): TodoListEvent()
    object OnQuickAddingTaskClick: TodoListEvent()
    data class OnSearchQueryChange(val searchQuery: String): TodoListEvent()
    data class OnSearchModeChange(val searchMode: Boolean): TodoListEvent()
}
