package com.jdcoding.todolist.feature_todo_list.presentation.todo_list

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jdcoding.todolist.R
import com.jdcoding.todolist.core.presentation.components.AddTextField
import com.jdcoding.todolist.core.util.UIEvent
import com.jdcoding.todolist.feature_todo_list.presentation.todo_list.components.*
import com.jdcoding.todolist.ui.theme.LocalSpacing
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val scaffoldState = rememberScaffoldState()
    val animationTime = if(state.animationEnabled) 500 else 0
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = state.tasks) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TodoListAppBar(scaffoldState)
        },
        drawerContent = {
            DrawerView(
                scaffoldState = scaffoldState,
                onCategorySelected = {
                    viewModel.onEvent(TodoListEvent.OnCategorySelected(it))
                },
                onCompletedTasksClicked = {
                    viewModel.onEvent(TodoListEvent.ShowCompletedTasks)
                },
                addCategory = {
                    viewModel.onEvent(TodoListEvent.AddCategory(it))
                },
                categories = state.categories
            )
        }
    ) {
        Column {
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.order_by)),
                style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    fontStyle = MaterialTheme.typography.body1.fontStyle,
                    fontSize = MaterialTheme.typography.body1.fontSize
                ),
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                onClick = {
                    viewModel.onEvent(TodoListEvent.OnToggleOrderSection)
                }
            )
            AnimatedVisibility(visible = state.orderSectionVisible) {
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                TasksOrderSection(
                    onOrderChange = {
                        viewModel.onEvent(TodoListEvent.OnTaskOrderChanged(it))
                    },
                    taskOrder = state.taskOrder
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                items(state.tasks, key = { it.task.id!! }) { task ->
                    AnimatedVisibility(
                        visible = when(state.taskType) {
                            is TaskType.Todo -> !task.task.completed
                            is TaskType.Completed -> task.task.completed
                        },
                        exit = slideOutHorizontally(
                            targetOffsetX = {screenWidth * 2},
                            animationSpec = tween(
                                durationMillis = animationTime,
                                easing = LinearEasing
                            )
                        ) + fadeOut(
                            animationSpec = tween(
                                durationMillis = animationTime,
                                easing = LinearEasing
                            )
                        )
                    ) {
                        TaskItem(

                            modifier = Modifier
                                .fillMaxWidth(),
                            taskState = task,
                            onClick = {
                                viewModel.onEvent(TodoListEvent.OnTaskClicked(task))
                            },
                            onLongClick = {
                                viewModel.onEvent(TodoListEvent.OnTaskLongClicked(task))
                            },
                            onCompletedClick = {
                                viewModel.onEvent(TodoListEvent.OnTaskCompletionChanged(task))
                            }
                        )
                    }
                }
            }
            AnimatedVisibility(visible = state.taskType is TaskType.Todo && state.category != null) {
                AddTextField(
                    value = state.quickAddTaskName,
                    showValidate = state.showAddTaskValidate,
                    onValueChange = {
                        viewModel.onEvent(TodoListEvent.OnQuickAddingTaskChanged(it))
                    },
                    onValidate = {
                        viewModel.onEvent(TodoListEvent.OnQuickAddingTaskClick)
                        keyboardController?.hide()
                    },
                    background = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}