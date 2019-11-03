package com.example.dontforget

import androidx.room.*

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user:UserEntity)

    @Update
    fun updateUser(user:UserEntity)

    @Delete
    fun deleteUser(user:UserEntity)

    @Query("SELECT * FROM UserEntity WHERE id == :ID")
    fun getUserByID(ID:Long): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE email == :Email")
    fun getUserByEmail(Email:String): List<UserEntity>

    @Query("SELECT * FROM UserEntity")
    fun getUsers(): List<UserEntity>
}