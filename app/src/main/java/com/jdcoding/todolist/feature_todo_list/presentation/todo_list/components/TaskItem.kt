package com.jdcoding.todolist.feature_todo_list.presentation.todo_list.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.jdcoding.todolist.R
import com.jdcoding.todolist.feature_todo_list.presentation.todo_list.TaskState
import com.jdcoding.todolist.ui.theme.LocalSpacing
import com.jdcoding.todolist.ui.theme.TransparentGreen
import java.text.DateFormat

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItem(
    taskState: TaskState,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onCompletedClick: (completed: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val deadlineFormatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT)
    val spacing = LocalSpacing.current
    val background = if(taskState.selected) TransparentGreen else MaterialTheme.colors.surface
    val textColor = if(taskState.task.isImportant) Color.Red else Color.Black
    Row(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    5.dp
                )
            )
            .padding(spacing.spaceExtraSmall)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(background)
            .padding(end = spacing.spaceMedium)
            .combinedClickable(
                onClick = {
                    onClick()
                },
                onLongClick = {
                    onLongClick()
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Checkbox(
                checked = taskState.task.completed,
                onCheckedChange = {
                    onCompletedClick(it)
                }
            )
        }
        Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
        Column (
            modifier = Modifier.padding(
                top = spacing.spaceExtraSmall,
                bottom = spacing.spaceExtraSmall
            )
                ) {
            Text(
                text = taskState.task.name,
                style = MaterialTheme.typography.body1,
                textDecoration = if (taskState.task.completed) TextDecoration.LineThrough else TextDecoration.None,
                color = textColor
            )
            if(taskState.task.deadline != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = deadlineFormatter.format(taskState.task.deadline),
                        style = MaterialTheme.typography.overline
                    )
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = stringResource(id = R.string.deadline),
                        Modifier.scale(0.5f)
                    )
                }
            }
            Text(
                text = taskState.task.category,
                style = MaterialTheme.typography.body2
            )
        }
    }
}