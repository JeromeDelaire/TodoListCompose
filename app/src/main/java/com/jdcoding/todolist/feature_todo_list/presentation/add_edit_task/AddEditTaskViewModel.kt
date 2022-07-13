package com.jdcoding.todolist.feature_todo_list.presentation.add_edit_task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdcoding.todolist.core.util.UIEvent
import com.jdcoding.todolist.feature_todo_list.domain.model.Task
import com.jdcoding.todolist.feature_todo_list.domain.use_case.AddTask
import com.jdcoding.todolist.feature_todo_list.domain.use_case.TodoListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val todoListUseCases: TodoListUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var state by mutableStateOf(Task(
        name = "",
        category = ""
    ))
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        savedStateHandle.get<Int>("taskId")?.let { taskId ->
            viewModelScope.launch {
                state = todoListUseCases.getTask(taskId)
            }
        }
    }

    fun onEvent(event: AddEditTaskEvent) {
        when(event) {
            is AddEditTaskEvent.OnCategoryChange -> {
                state = state.copy(
                    category = event.category
                )
            }
            is AddEditTaskEvent.OnTaskNameChange -> {
                state = state.copy(
                    name = event.taskName
                )
            }
            is AddEditTaskEvent.OnDeadlineChange -> {
                state = state.copy(
                    deadline = event.date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                )
            }
            is AddEditTaskEvent.OnImportantChange -> {
                state = state.copy(
                    isImportant = event.important
                )
            }
            is AddEditTaskEvent.OnTaskSave -> {
                viewModelScope.launch {
                    when(val result = todoListUseCases.addTask(state)) {
                        is  AddTask.Result.Success -> {
                            _uiEvent.send(UIEvent.NavigateUp)
                        }
                        is  AddTask.Result.Error -> {
                            _uiEvent.send(UIEvent.ShowSnackbar(
                                message = result.message
                            ))
                        }
                    }
                }
            }
        }
    }
}