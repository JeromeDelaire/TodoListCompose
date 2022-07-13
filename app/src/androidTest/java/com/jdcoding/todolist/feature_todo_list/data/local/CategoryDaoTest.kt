package com.jdcoding.todolist.feature_todo_list.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.jdcoding.todolist.feature_todo_list.data.local.entity.CategoryEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@RunWith(AndroidJUnit4::class)
@SmallTest
class CategoryDaoTest {

    private lateinit var database: TodoListDatabase
    private lateinit var dao: CategoryDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TodoListDatabase::class.java,
        )
            .allowMainThreadQueries()
            .build()
        dao = database.categoryDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCategory() = runBlocking {
        val category = CategoryEntity("Shopping")
        dao.insertCategory(category)

        val categories = async {
            dao.getCategories().take(1).toList()
        }

        assertThat(categories.await()[0]).contains(category)
    }

    @Test
    fun deleteCategory() = runBlocking {
        val category = CategoryEntity("Shopping")
        dao.insertCategory(category)
        dao.deleteCategory(category)

        val categories = async {
            dao.getCategories().take(1).toList()
        }

        assertThat(categories.await()[0]).doesNotContain(category)
    }
}