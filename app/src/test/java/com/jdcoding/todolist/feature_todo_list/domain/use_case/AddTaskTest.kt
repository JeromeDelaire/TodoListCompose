package com.jdcoding.todolist.feature_todo_list.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.jdcoding.todolist.feature_todo_list.data.local.FakeTodoListRepositoryImpl
import com.jdcoding.todolist.feature_todo_list.domain.model.Task
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddTaskTest {

    private lateinit var addTask: AddTask

    @Before
    fun setup() {
        addTask = AddTask(FakeTodoListRepositoryImpl())
    }

    @Test
    fun `add task with blank name return error`() = runBlocking {
        val result = addTask(
            Task(
                name = "    ",
                category = "Shopping"
            )
        )
        var error = false
        error = when(result) {
            is AddTask.Result.Success -> {
                false
            }

            is AddTask.Result.Error -> {
                true
            }
        }
        assertThat(error).isTrue()
    }

    @Test
    fun `add task with blank category return error`() = runBlocking {
        val result = addTask(
            Task(
                name = "Eggs",
                category = ""
            )
        )
        val error = when(result) {
            is AddTask.Result.Success -> {
                false
            }

            is AddTask.Result.Error -> {
                true
            }
        }
        assertThat(error).isTrue()
    }

    @Test
    fun `add valid task return success`() = runBlocking {
        val result = addTask(
            Task(
                name = "Eggs",
                category = "Shopping"
            )
        )
        val error = when(result) {
            is AddTask.Result.Success -> {
                false
            }

            is AddTask.Result.Error -> {
                true
            }
        }
        assertThat(error).isFalse()
    }
}