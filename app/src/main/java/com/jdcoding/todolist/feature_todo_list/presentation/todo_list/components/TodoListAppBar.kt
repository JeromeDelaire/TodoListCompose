package com.jdcoding.todolist.feature_todo_list.presentation.todo_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jdcoding.todolist.R
import com.jdcoding.todolist.core.presentation.components.SearchView
import com.jdcoding.todolist.feature_todo_list.presentation.todo_list.TodoListEvent
import com.jdcoding.todolist.feature_todo_list.presentation.todo_list.TodoListViewModel
import kotlinx.coroutines.launch

@Composable
fun TodoListAppBar(
    scaffoldState: ScaffoldState,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.menu)
                )
            }
        },
        actions = {
            AnimatedVisibility(visible = !state.selectionMode && state.searchMode) {
                Row {
                    SearchView(
                        onSearchTextChanged = {
                            viewModel.onEvent(TodoListEvent.OnSearchQueryChange(it))
                        },
                        placeholderText = stringResource(id = R.string.search_for_tasks),
                        onClearClick = {
                            viewModel.onEvent(TodoListEvent.OnSearchModeChange(false))
                        },
                        searchText = state.searchQuery
                    )
                }
            }
            AnimatedVisibility(visible = !state.selectionMode && !state.searchMode) {
                Row {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(TodoListEvent.OnSearchModeChange(true))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search_for_tasks)
                        )
                    }
                }
            }
            AnimatedVisibility(visible = state.selectionMode) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(TodoListEvent.DeleteSelectedTasks)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete_selected_tasks)
                    )
                }
            }
        }
    )
}