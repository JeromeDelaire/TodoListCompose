package com.jdcoding.todolist.feature_todo_list.presentation.todo_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdcoding.todolist.R
import com.jdcoding.todolist.core.navigation.Route
import com.jdcoding.todolist.core.util.UIEvent
import com.jdcoding.todolist.core.util.UIText
import com.jdcoding.todolist.feature_todo_list.domain.model.Task
import com.jdcoding.todolist.feature_todo_list.domain.use_case.AddCategory
import com.jdcoding.todolist.feature_todo_list.domain.use_case.TodoListUseCases
import com.jdcoding.todolist.feature_todo_list.presentation.todo_list.components.TaskType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoListUseCases: TodoListUseCases
): ViewModel(){

    var state by mutableStateOf(TodoListState())
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getCategoriesJob: Job? = null
    private var getTasksJob: Job? = null

    init {
        initCategories()
        refreshTasks()
    }

    fun onEvent(event: TodoListEvent) {
        when(event) {
            is TodoListEvent.OnAddTaskClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UIEvent.Navigate(Route.ADD_EDIT_TASK)
                    )
                }
            }
            is TodoListEvent.OnCategorySelected -> {
                state = state.copy(
                    category = event.categoryName,
                    taskType = TaskType.Todo,
                    animationEnabled = false
                )
                refreshTasks()
            }
            is TodoListEvent.OnTaskClicked -> {
                if(state.selectionMode) {
                    state = state.copy(
                        tasks = state.tasks.map {
                            if (it.task == event.task.task) {
                                it.copy(
                                    selected = !it.selected
                                )
                            } else it
                        },
                    )
                    state = state.copy(
                        selectionMode = (state.tasks.count { it.selected }) != 0
                    )
                } else {
                    viewModelScope.launch {
                        _uiEvent.send(
                            UIEvent.Navigate(
                                Route.ADD_EDIT_TASK +
                                        "/${event.task.task.id}")
                        )
                    }
                }
            }
            is TodoListEvent.OnTaskCompletionChanged -> {
                viewModelScope.launch {
                    state = state.copy(
                        animationEnabled = true
                    )
                    todoListUseCases.addTask(
                        event.task.task.copy(
                            completed = !event.task.task.completed,
                        )
                    )
                }
            }
            is TodoListEvent.OnTaskLongClicked -> {
                state = state.copy(
                    tasks = state.tasks.map {
                        if(event.task == it) {
                            it.copy(
                                selected = true
                            )
                        } else it
                    },
                    selectionMode = true
                )
            }
            is TodoListEvent.OnTaskOrderChanged -> {
                state = state.copy(
                    taskOrder = event.taskOrder
                )
                refreshTasks()
            }
            is TodoListEvent.OnToggleOrderSection -> {
                state = state.copy(
                    orderSectionVisible = !state.orderSectionVisible
                )
            }
            is TodoListEvent.ShowCompletedTasks -> {
                state = state.copy(
                    taskType = TaskType.Completed,
                    category = null,
                    animationEnabled = false
                )
            }
            is TodoListEvent.DeleteSelectedTasks -> {
                viewModelScope.launch {
                    state.tasks.forEach { task ->
                        if(task.selected) {
                            todoListUseCases.deleteTask(task.task)
                        }
                    }
                }
            }
            is TodoListEvent.AddCategory -> {
                viewModelScope.launch {
                    when(todoListUseCases.addCategory(event.categoryName)) {
                        is AddCategory.Result.Error -> {
                            _uiEvent.send(UIEvent.ShowSnackbar(
                                UIText.StringResource(R.string.error_during_add_category)
                            ))
                        }
                        is AddCategory.Result.Success -> {

                        }
                    }
                }
            }
            is TodoListEvent.OnQuickAddingTaskChanged -> {
                state = state.copy(
                    quickAddTaskName = event.taskName,
                    showAddTaskValidate = event.taskName.isNotBlank()
                )
            }
            is TodoListEvent.OnQuickAddingTaskClick -> {
                if(state.category != null) {
                    viewModelScope.launch {
                        todoListUseCases.addTask(
                            Task(
                                name = state.quickAddTaskName,
                                category = state.category!!
                            )
                        )
                        state = state.copy(
                            quickAddTaskName = "",
                            showAddTaskValidate = false
                        )
                    }
                }
            }
            is TodoListEvent.OnSearchQueryChange -> {
                state = state.copy(
                    searchQuery = event.searchQuery
                )
                refreshTasks()
            }
            is TodoListEvent.OnSearchModeChange -> {
                state = state.copy(
                    searchMode = event.searchMode,
                    searchQuery = ""
                )
                if(!event.searchMode) {
                    refreshTasks()
                }
            }
        }
    }

    private fun initCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = todoListUseCases.getCategories()
            .onEach {
                state = state.copy(
                    categories = it
                )
            }
            .launchIn(viewModelScope)
    }

    private fun refreshTasks() {
        getTasksJob?.cancel()
        getTasksJob = todoListUseCases.getTasks(
            taskOrder = state.taskOrder,
            state.category,
            state.searchQuery
        ).onEach { tasks ->
            val updatedTasks = tasks.map {
                TaskState(
                    task = it
                )
            }
            state = state.copy(
                tasks = updatedTasks,
                selectionMode = false
            )
        }.launchIn(viewModelScope)
    }
}