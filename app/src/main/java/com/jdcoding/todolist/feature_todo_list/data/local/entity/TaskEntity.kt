package com.jdcoding.todolist.feature_todo_list.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = CategoryEntity::class,
        parentColumns = ["name"],
        childColumns = ["category"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TaskEntity(
    val name: String,
    val completed: Boolean = false,
    val created: Long = System.currentTimeMillis(),
    val deadline: Long? = null,
    val isImportant: Boolean = false,
    val category: String,
    @PrimaryKey val id: Int? = null
)
