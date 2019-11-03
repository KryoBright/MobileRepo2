package com.example.dontforget

import androidx.room.*

@Dao
interface TaskDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task:TaskEntity)

    @Update
    fun updateTask(task:TaskEntity)

    @Delete
    fun deleteTask(task:TaskEntity)

    @Query("SELECT * FROM TaskEntity WHERE task_id == :ID")//Пологаю,селекты не работают,поэтому пока я беру все
    fun getTaskByID(ID:Long): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity")
    fun getTasks(): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity WHERE category_id == :Category_ID")
    fun getTasksWithCategory(Category_ID:Long): List<TaskEntity>

}