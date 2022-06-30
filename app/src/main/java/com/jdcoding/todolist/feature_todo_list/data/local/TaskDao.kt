package com.jdcoding.todolist.feature_todo_list.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.jdcoding.todolist.feature_todo_list.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM taskentity")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM taskentity WHERE category = :category")
    fun getTasksForCategory(category: String): Flow<List<TaskEntity>>

    fun getTasks(category: String?): Flow<List<TaskEntity>> {
        return if(category == null) {
            getTasks()
        } else {
            getTasksForCategory(category)
        }
    }
}