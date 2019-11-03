package com.example.dontforget

import androidx.room.*

@Dao
interface PriorityDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriority(priority:PriorityEntity)

    @Update
    fun updatePriority(priority:PriorityEntity)

    @Delete
    fun deletePriority(priority:PriorityEntity)

    @Query("SELECT * FROM PriorityEntity WHERE priority_id == :ID")
    fun getPriorityByID(ID:Long): List<PriorityEntity>

    @Query("SELECT * FROM PriorityEntity")
    fun getPriorities(): List<PriorityEntity>
}