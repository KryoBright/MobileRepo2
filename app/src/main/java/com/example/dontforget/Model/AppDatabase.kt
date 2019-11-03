package com.example.dontforget

import android.content.Context
import androidx.room.*

@Database(entities = [UserEntity::class, CategoryEntity::class, PriorityEntity::class, TaskEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UserDAO(): UserDAO
    abstract fun PriorityDAO(): PriorityDAO
    abstract fun CategoryDAO(): CategoryDAO
    abstract fun TaskDAO(): TaskDAO

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}