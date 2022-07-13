package com.jdcoding.todolist.feature_todo_list.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.Query
import com.jdcoding.todolist.feature_todo_list.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = ABORT)
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM categoryentity")
    fun getCategories(): Flow<List<CategoryEntity>>
}