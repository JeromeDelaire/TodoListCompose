package com.jdcoding.todolist.feature_todo_list.domain.util

sealed class TaskOrder(val orderType: OrderType) {
    class Date(orderType: OrderType): TaskOrder(orderType)
    class Name(orderType: OrderType): TaskOrder(orderType)
    class Deadline(orderType: OrderType): TaskOrder(orderType)

    fun copy(orderType: OrderType): TaskOrder {
        return when(this) {
            is Name -> Name(orderType)
            is Date -> Date(orderType)
            is Deadline -> Deadline(orderType)
        }
    }
}
