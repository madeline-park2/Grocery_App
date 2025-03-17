package com.zybooks.groceryapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert
    suspend fun insert(food: Food): Long

    @Query("SELECT * FROM Food WHERE inPantry = 1 AND id = :id")
    fun getPantryItem(id: Long): Flow<Food>

    @Query("SELECT * FROM Food WHERE inGrocery = 1 AND id = :id")
    fun getGroceryItem(id: Long): Flow<Food>

    @Query("SELECT * FROM Food WHERE inPantry = 1")
    fun getAllPantry(): Flow<List<Food>>

    @Query("SELECT * FROM Food WHERE inGrocery = 1")
    fun getAllGrocery(): Flow<List<Food>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFood(subject: Food): Long

    @Update
    fun updateFood(food: Food)

    @Delete
    fun deleteFood(food: Food)
}