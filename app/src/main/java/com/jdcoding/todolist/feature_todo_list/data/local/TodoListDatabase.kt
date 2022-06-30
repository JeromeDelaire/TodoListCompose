package com.jdcoding.todolist.feature_todo_list.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jdcoding.todolist.di.ApplicationScope
import com.jdcoding.todolist.feature_todo_list.data.local.entity.CategoryEntity
import com.jdcoding.todolist.feature_todo_list.data.local.entity.TaskEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@Database(
    entities = [CategoryEntity::class, TaskEntity::class],
    version = 1
)
abstract class TodoListDatabase: RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val categoryDao: CategoryDao

    class Callback @Inject constructor(
        private val database: TodoListDatabase,
        @ApplicationScope private val applicationScope: CoroutineScope,
        @ApplicationContext private val applicationContext: Context
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {

        }
    }
}