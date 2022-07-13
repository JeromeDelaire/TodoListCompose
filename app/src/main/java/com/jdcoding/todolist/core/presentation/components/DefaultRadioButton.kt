package com.jdcoding.todolist.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jdcoding.todolist.ui.theme.LocalSpacing

@Composable
fun DefaultRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    var spacing = LocalSpacing.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.secondary,
                unselectedColor = MaterialTheme.colors.secondary
            )
        )
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDefaultRadioButton() {
    DefaultRadioButton(text = "By date", selected = true, onSelect = { /*TODO*/ })
}