package com.jdcoding.todolist.feature_todo_list.domain.use_case

import com.jdcoding.todolist.feature_todo_list.data.local.FakeTodoListRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class AddCategoryTest {

    private lateinit var addCategory: AddCategory

    @Before
    fun setup() {
        addCategory = AddCategory(FakeTodoListRepositoryImpl())
    }

    @Test
    fun `add category with blank name return error`() = runBlocking {
        val result = addCategory("    ")
        val error = when(result) {
            is AddCategory.Result.Success -> {
                false
            }

            is AddCategory.Result.Error -> {
                true
            }
        }
        assertThat(error).isTrue()
    }

    @Test
    fun `add category with valid name return success`() = runBlocking {
        val result = addCategory("Shopping")
        val error = when(result) {
            is AddCategory.Result.Success -> {
                false
            }

            is AddCategory.Result.Error -> {
                true
            }
        }
        assertThat(error).isFalse()
    }
}