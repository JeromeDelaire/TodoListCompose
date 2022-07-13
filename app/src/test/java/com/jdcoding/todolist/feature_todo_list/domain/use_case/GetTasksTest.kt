package com.jdcoding.todolist.feature_todo_list.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.jdcoding.todolist.feature_todo_list.data.local.FakeTodoListRepositoryImpl
import com.jdcoding.todolist.feature_todo_list.domain.util.OrderType
import com.jdcoding.todolist.feature_todo_list.domain.util.TaskOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test

class GetTasksTest {

    private lateinit var getTasks: GetTasks

    @Before
    fun setup() {
        getTasks = GetTasks(FakeTodoListRepositoryImpl())
    }

    @Test
    fun `get task without category return all tasks`() = runBlocking{
        val tasks = withContext(Dispatchers.Default) {
            getTasks(TaskOrder.Name(OrderType.Ascending)).take(1).toList()
        }[0]
        assertThat(tasks.size).isEqualTo(4)
    }

    @Test
    fun `get task with category return only tasks for this category`() = runBlocking {
        val tasks = withContext(Dispatchers.Default) {
            getTasks(TaskOrder.Name(OrderType.Ascending), "Shopping").take(1).toList()
        }[0]

        assertThat(tasks.size).isEqualTo(2)
        assertThat(tasks[0].id).isEqualTo(3)
        assertThat(tasks[1].id).isEqualTo(1)
    }

    @Test
    fun `order tasks by importance and name`() = runBlocking {
        val tasks = withContext(Dispatchers.Default) {
            getTasks(TaskOrder.Name(OrderType.Ascending)).take(1).toList()
        }[0]

        assertThat(tasks.size).isEqualTo(4)
        assertThat(tasks[0].id).isEqualTo(4)
        assertThat(tasks[1].id).isEqualTo(3)
        assertThat(tasks[2].id).isEqualTo(2)
        assertThat(tasks[3].id).isEqualTo(1)
    }

    @Test
    fun `order tasks by importance and name descending`() = runBlocking {
        val tasks = withContext(Dispatchers.Default) {
            getTasks(TaskOrder.Name(OrderType.Descending)).take(1).toList()
        }[0]

        assertThat(tasks.size).isEqualTo(4)
        assertThat(tasks[0].id).isEqualTo(3)
        assertThat(tasks[1].id).isEqualTo(4)
        assertThat(tasks[2].id).isEqualTo(1)
        assertThat(tasks[3].id).isEqualTo(2)
    }

    @Test
    fun `order tasks by importance and date`() = runBlocking {
        val tasks = withContext(Dispatchers.Default) {
            getTasks(TaskOrder.Date(OrderType.Ascending)).take(1).toList()
        }[0]

        assertThat(tasks.size).isEqualTo(4)
        assertThat(tasks[0].id).isEqualTo(4)
        assertThat(tasks[1].id).isEqualTo(3)
        assertThat(tasks[2].id).isEqualTo(1)
        assertThat(tasks[3].id).isEqualTo(2)
    }

    @Test
    fun `order tasks by importance and date descending`() = runBlocking {
        val tasks = withContext(Dispatchers.Default) {
            getTasks(TaskOrder.Date(OrderType.Descending)).take(1).toList()
        }[0]

        assertThat(tasks.size).isEqualTo(4)
        assertThat(tasks[0].id).isEqualTo(3)
        assertThat(tasks[1].id).isEqualTo(4)
        assertThat(tasks[2].id).isEqualTo(2)
        assertThat(tasks[3].id).isEqualTo(1)
    }

    @Test
    fun `order tasks by importance and deadline`() = runBlocking {
        val tasks = withContext(Dispatchers.Default) {
            getTasks(TaskOrder.Deadline(OrderType.Ascending)).take(1).toList()
        }[0]

        assertThat(tasks.size).isEqualTo(4)
        assertThat(tasks[0].id).isEqualTo(3)
        assertThat(tasks[1].id).isEqualTo(4)
        assertThat(tasks[2].id).isEqualTo(2)
        assertThat(tasks[3].id).isEqualTo(1)
    }

    @Test
    fun `order tasks by importance and deadline descending`() = runBlocking {
        val tasks = withContext(Dispatchers.Default) {
            getTasks(TaskOrder.Deadline(OrderType.Descending)).take(1).toList()
        }[0]

        assertThat(tasks.size).isEqualTo(4)
        assertThat(tasks[0].id).isEqualTo(4)
        assertThat(tasks[1].id).isEqualTo(3)
        assertThat(tasks[2].id).isEqualTo(1)
        assertThat(tasks[3].id).isEqualTo(2)
    }
}