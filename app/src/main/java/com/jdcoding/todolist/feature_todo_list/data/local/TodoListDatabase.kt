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
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [CategoryEntity::class, TaskEntity::class],
    version = 1
)
abstract class TodoListDatabase: RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val categoryDao: CategoryDao

    class Callback @Inject constructor(
        private val database: Provider<TodoListDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val taskDao = database.get().taskDao
            val categoryDao = database.get().categoryDao

            applicationScope.launch {

                categoryDao.insertCategory(
                    CategoryEntity("Shopping")
                )

                categoryDao.insertCategory(
                    CategoryEntity("Job")
                )

                taskDao.insertTask(
                    TaskEntity(
                        name = "Buy eggs",
                        category = "Shopping"
                    )
                )

                taskDao.insertTask(
                    TaskEntity(
                        name = "Buy noodles",
                        category = "Shopping",
                        isImportant = true
                    )
                )

                taskDao.insertTask(
                    TaskEntity(
                        name = "Buy chips",
                        category = "Shopping",
                        isImportant = true,
                        completed = false,
                        deadline = System.currentTimeMillis() - 100000
                    )
                )

                taskDao.insertTask(
                    TaskEntity(
                        name = "Trello",
                        category = "Job",
                        isImportant = true,
                        completed = false,
                        deadline = System.currentTimeMillis()
                    )
                )

                taskDao.insertTask(
                    TaskEntity(
                        name = "MAJ",
                        category = "Job",
                        isImportant = true,
                        completed = false
                    )
                )

                taskDao.insertTask(
                    TaskEntity(
                        name = "Test",
                        category = "Job",
                        isImportant = true,
                        completed = false
                    )
                )

            }
        }
    }
}