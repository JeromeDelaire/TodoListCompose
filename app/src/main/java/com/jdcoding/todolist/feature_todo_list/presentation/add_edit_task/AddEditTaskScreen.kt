package com.jdcoding.todolist.feature_todo_list.presentation.add_edit_task

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddEditTaskScreen(
    viewModel: AddEditTaskViewModel = hiltViewModel(),
) {
    val state = viewModel.state

    Column {
        OutlinedTextField(
            value = state.name,
            onValueChange = {
                viewModel.onEvent(AddEditTaskEvent.OnTaskNameChange(it))
            }
        )
        Checkbox(
            checked = state.isImportant,
            onCheckedChange = {
                viewModel.onEvent(AddEditTaskEvent.OnImportantChange(it))
            }
        )
    }
}