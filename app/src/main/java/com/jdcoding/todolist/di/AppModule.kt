package com.jdcoding.todolist.di

import android.app.Application
import androidx.room.Room
import com.jdcoding.todolist.feature_todo_list.data.local.TodoListDatabase
import com.jdcoding.todolist.feature_todo_list.data.repository.TodoListRepositoryImpl
import com.jdcoding.todolist.feature_todo_list.domain.repository.TodoListRepository
import com.jdcoding.todolist.feature_todo_list.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoListDatabase(app: Application, callback: TodoListDatabase.Callback): TodoListDatabase {
        return Room.databaseBuilder(
            app,
            TodoListDatabase::class.java,
            "todo_list_db"
        )
            .addCallback(callback)
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoListRepository(db: TodoListDatabase): TodoListRepository {
        return TodoListRepositoryImpl(
            taskDao = db.taskDao,
            categoryDao = db.categoryDao
        )
    }

    @Provides
    @Singleton
    fun provideTodoListUseCase(repository: TodoListRepository): TodoListUseCases {
        return TodoListUseCases(
            addCategory = AddCategory(repository),
            addTask = AddTask(repository),
            deleteCategory = DeleteCategory(repository),
            deleteTask = DeleteTask(repository),
            getCategory = GetCategory(repository),
            getTasks = GetTasks(repository),
        )
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope