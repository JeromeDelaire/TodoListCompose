package com.jdcoding.todolist.feature_todo_list.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.jdcoding.todolist.feature_todo_list.data.local.entity.CategoryEntity
import com.jdcoding.todolist.feature_todo_list.data.local.entity.TaskEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDaoTest {

    private lateinit var database: TodoListDatabase
    private lateinit var dao: TaskDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TodoListDatabase::class.java,
        )
            .allowMainThreadQueries()
            .build()
        dao = database.taskDao
        runBlocking {
            database.categoryDao.insertCategory(CategoryEntity("Shopping"))
            database.categoryDao.insertCategory(CategoryEntity("Work"))
        }
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertTask() = runBlocking {
        val task = TaskEntity(
            name = "eggs",
            completed = false,
            created = System.currentTimeMillis(),
            deadline = null,
            isImportant = false,
            category = "Shopping",
            id = 1
        )
        dao.insertTask(task)

        val tasks = async {
            dao.getTasks().take(1).toList()
        }

        assertThat(tasks.await()[0]).contains(task)
    }

    @Test
    fun deleteTask() = runBlocking {
        val task = TaskEntity(
            name = "eggs",
            completed = false,
            created = System.currentTimeMillis(),
            deadline = null,
            isImportant = false,
            category = "Shopping",
            id = 1
        )
        dao.insertTask(task)
        dao.deleteTask(task)

        val tasks = async {
            dao.getTasks().take(1).toList()
        }

        assertThat(tasks.await()[0]).doesNotContain(task)
    }

    @Test
    fun getTasksForCategory() = runBlocking {
        val task1 = TaskEntity(
            name = "eggs",
            completed = false,
            created = System.currentTimeMillis(),
            deadline = null,
            isImportant = false,
            category = "Shopping",
            id = 1
        )
        val task2 = TaskEntity(
            name = "develop app",
            completed = false,
            created = System.currentTimeMillis(),
            deadline = null,
            isImportant = false,
            category = "Work",
            id = 2
        )
        dao.insertTask(task1)
        dao.insertTask(task2)

        val shoppingTasks = async {
            dao.getTasks("Shopping").take(1).toList()
        }.await()

        assertThat(shoppingTasks[0]).contains(task1)
        assertThat(shoppingTasks[0]).doesNotContain(task2)
    }
}