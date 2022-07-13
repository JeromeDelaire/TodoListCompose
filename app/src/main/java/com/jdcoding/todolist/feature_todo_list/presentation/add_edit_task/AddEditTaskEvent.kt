package com.jdcoding.todolist.feature_todo_list.presentation.add_edit_task

import java.time.LocalDate
import java.time.LocalDateTime

sealed class AddEditTaskEvent {
    data class OnTaskNameChange(val taskName: String): AddEditTaskEvent()
    data class OnImportantChange(val important: Boolean): AddEditTaskEvent()
    data class OnDeadlineChange(val date: LocalDateTime): AddEditTaskEvent()
    data class OnCategoryChange(val category: String): AddEditTaskEvent()
    object OnTaskSave: AddEditTaskEvent()
}