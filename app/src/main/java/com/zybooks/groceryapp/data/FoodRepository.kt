package com.zybooks.groceryapp.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodRepository(context: Context) {

    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {
                addStarterData()
            }
        }
    }

    private val database: FoodDatabase = Room.databaseBuilder(
        context,
        FoodDatabase::class.java,
        "food.db"
    )
        .addCallback(databaseCallback)
        .build()

    private val foodDao = database.foodDao()

    fun getAllPantry() = foodDao.getAllPantry()
    fun getAllGrocery() = foodDao.getAllGrocery()


    fun addFood(food: Food) {
        if (food.foodItem.trim() != "") {
            CoroutineScope(Dispatchers.IO).launch {
                food.id = foodDao.addFood(food)
            }
        }
    }

    fun deleteFood(food: Food) {
        CoroutineScope(Dispatchers.IO).launch {
            foodDao.deleteFood(food)
        }
    }

    private fun addStarterData() {
        foodDao.addFood(
            Food(
                foodItem = "Apple",
                quantity = 4,
                inPantry = 1,
                inGrocery = 0
            )
        )

        foodDao.addFood(
            Food(
                foodItem = "Ice Cream",
                quantity = 1,
                inPantry = 0,
                inGrocery = 1
            )
        )

        foodDao.addFood(
            Food(
                foodItem = "Bread",
                quantity = 1,
                inPantry = 1,
                inGrocery = 0
            )
        )
    }
}

