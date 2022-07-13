package com.jdcoding.todolist.feature_todo_list.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
