package com.jdcoding.todolist.feature_todo_list.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.jdcoding.todolist.feature_todo_list.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM categoryentity")
    fun getCategories(): Flow<List<CategoryEntity>>
}