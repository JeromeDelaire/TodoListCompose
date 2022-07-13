package com.jdcoding.todolist.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.jdcoding.todolist.R
import com.jdcoding.todolist.ui.theme.LocalSpacing

@Composable
fun AddTextField(
    value: String,
    modifier: Modifier = Modifier,
    showValidate: Boolean = false,
    background: Color = Color.White,
    textColor: Color = Color.Black,
    onValueChange: (String) -> Unit,
    onValidate: () -> Unit
) {
    val spacing = LocalSpacing.current
    Box (
        modifier = modifier
            .background(background),
    )
    {
        Row {
            TextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        bottom = spacing.spaceSmall,
                        start = spacing.spaceSmall,
                        end = spacing.spaceSmall
                        ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = textColor,
                    backgroundColor = Color.Transparent,
                    cursorColor = textColor,
                    unfocusedIndicatorColor = textColor,
                    focusedIndicatorColor = textColor
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.add_task),
                        color = Color.LightGray,
                        style = MaterialTheme.typography.body2
                    )
                },
                textStyle = MaterialTheme.typography.body2,
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        if(showValidate) onValidate()
                        defaultKeyboardAction(ImeAction.Done)
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
            )
            AnimatedVisibility(visible = showValidate) {
                IconButton(
                    onClick = onValidate,
                    modifier = Modifier
                        .padding(
                            top = spacing.spaceSmall,
                            bottom = spacing.spaceSmall,
                            end = spacing.spaceSmall,
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = stringResource(id = R.string.add_task),
                        tint = textColor
                    )
                }
            }
        }
    }
}