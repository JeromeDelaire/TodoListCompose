package com.jdcoding.todolist.feature_todo_list.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.jdcoding.todolist.feature_todo_list.data.local.entity.TaskEntity
import com.jdcoding.todolist.feature_todo_list.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM taskentity WHERE name LIKE '%' || :searchQuery || '%'")
    fun getTasks(searchQuery: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM taskentity WHERE category = :category AND name LIKE '%' || :searchQuery || '%'")
    fun getTasksForCategory(category: String, searchQuery: String): Flow<List<TaskEntity>>

    fun getTasks(category: String?, searchQuery: String): Flow<List<TaskEntity>> {
        return if(category == null) {
            getTasks(searchQuery)
        } else {
            getTasksForCategory(category, searchQuery)
        }
    }

    @Query("SELECT * FROM taskentity WHERE id = :id")
    suspend fun getTask(id: Int): Task
}