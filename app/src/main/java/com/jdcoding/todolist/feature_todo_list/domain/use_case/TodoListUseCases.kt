package com.jdcoding.todolist.feature_todo_list.domain.use_case

data class TodoListUseCases (
    val addCategory: AddCategory,
    val addTask: AddTask,
    val deleteCategory: DeleteCategory,
    val deleteTask: DeleteTask,
    val getCategories: GetCategories,
    val getTasks: GetTasks,
    val getTask: GetTask
)