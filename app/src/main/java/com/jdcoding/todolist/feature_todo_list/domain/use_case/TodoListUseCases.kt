package com.jdcoding.todolist.feature_todo_list.domain.use_case

data class TodoListUseCases (
    val addCategory: AddCategory,
    val addTask: AddTask,
    val deleteCategory: DeleteCategory,
    val deleteTask: DeleteTask,
    val getCategory: GetCategory,
    val getTasks: GetTasks
)