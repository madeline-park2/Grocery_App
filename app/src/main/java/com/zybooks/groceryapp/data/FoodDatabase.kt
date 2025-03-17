package com.zybooks.groceryapp.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Food::class], version = 1)

abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}