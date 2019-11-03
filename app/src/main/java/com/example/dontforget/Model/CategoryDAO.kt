package com.example.dontforget

import androidx.room.*


@Dao
interface CategoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category:CategoryEntity)

    @Update
    fun updateCategory(category:CategoryEntity)

    @Delete
    fun deleteCategory(category:CategoryEntity)

    @Query("SELECT * FROM CategoryEntity WHERE category_id == :ID")
    fun getCategoryByID(ID:Long): List<CategoryEntity>

    @Query("SELECT * FROM CategoryEntity")
    fun getCategories(): List<CategoryEntity>
}