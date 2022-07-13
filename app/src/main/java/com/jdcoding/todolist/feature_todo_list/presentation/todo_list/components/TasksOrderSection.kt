package com.jdcoding.todolist.feature_todo_list.presentation.todo_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jdcoding.todolist.R
import com.jdcoding.todolist.core.presentation.components.DefaultRadioButton
import com.jdcoding.todolist.feature_todo_list.domain.util.OrderType
import com.jdcoding.todolist.feature_todo_list.domain.util.TaskOrder

@Composable
fun TasksOrderSection(
    onOrderChange: (TaskOrder) -> Unit,
    modifier: Modifier = Modifier,
    taskOrder: TaskOrder = TaskOrder.Name(OrderType.Ascending)
) {
    Column (
        modifier = modifier
    ){
        Row {
            DefaultRadioButton(
                text = stringResource(id = R.string.name),
                selected = taskOrder is TaskOrder.Name,
                onSelect = { onOrderChange(TaskOrder.Name(taskOrder.orderType)) }
            )

            DefaultRadioButton(
                text = stringResource(id = R.string.date),
                selected = taskOrder is TaskOrder.Date,
                onSelect = { onOrderChange(TaskOrder.Date(taskOrder.orderType)) }
            )

            DefaultRadioButton(
                text = stringResource(id = R.string.deadline),
                selected = taskOrder is TaskOrder.Deadline,
                onSelect = { onOrderChange(TaskOrder.Deadline(taskOrder.orderType)) }
            )

        }

        Row {
            DefaultRadioButton(
                text = stringResource(id = R.string.ascending),
                selected = taskOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(taskOrder.copy(OrderType.Ascending)) }
            )

            DefaultRadioButton(
                text = stringResource(id = R.string.descending),
                selected = taskOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(taskOrder.copy(OrderType.Descending)) }
            )
        }
    }
}