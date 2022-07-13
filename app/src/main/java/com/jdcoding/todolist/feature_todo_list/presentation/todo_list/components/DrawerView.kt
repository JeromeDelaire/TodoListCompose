package com.jdcoding.todolist.feature_todo_list.presentation.todo_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jdcoding.todolist.R
import com.jdcoding.todolist.core.presentation.components.AddTextField
import com.jdcoding.todolist.ui.theme.LocalSpacing
import kotlinx.coroutines.launch

@Composable
fun DrawerView(
    scaffoldState: ScaffoldState,
    onCategorySelected: (String?) -> Unit,
    onCompletedTasksClicked: () -> Unit,
    addCategory: (String) -> Unit,
    categories: List<String> = emptyList()
) {
    val scope = rememberCoroutineScope()
    var showAddCategoryEditText by remember { mutableStateOf(false) }
    var categoryToAdd by remember { mutableStateOf("") }
    DrawerHeader()
    DrawerSection(
        title = stringResource(id = R.string.categories)
    )
    Text(
        text = stringResource(id = R.string.all),
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(
                start = 16.dp,
                bottom = 16.dp,
                top = 16.dp
            )
            .clickable {
                onCategorySelected(null)
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
            .fillMaxWidth()
    )
    LazyColumn {
        items(categories) { category ->
            Text(
                text = category,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        bottom = 16.dp
                    )
                    .clickable {
                        onCategorySelected(category)
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                    .fillMaxWidth()
            )
        }
    }
    Text(
        text = stringResource(id = R.string.completed),
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(
                start = 16.dp,
                bottom = 16.dp
            )
            .clickable {
                scope.launch {
                    onCompletedTasksClicked()
                    scaffoldState.drawerState.close()
                }
            }
            .fillMaxWidth()
    )
    AnimatedVisibility(visible = showAddCategoryEditText) {
        AddTextField(
            value = categoryToAdd,
            onValueChange = { categoryToAdd = it },
            showValidate = categoryToAdd.isNotEmpty(),
            onValidate = {
                addCategory(categoryToAdd)
                showAddCategoryEditText = false
                categoryToAdd = ""
            }
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                showAddCategoryEditText = true
            }
            .padding(start = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.add_category)
        )
        Text(
            text = stringResource(id = R.string.add_category),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DrawerHeader() {
    Column(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h4
        )
    }
}

@Composable
fun DrawerSection(
    title: String,
    titleColor: Color = Color.Black,
) {
    val spacing = LocalSpacing.current
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(spacing.spaceMedium)
        )
    }
}