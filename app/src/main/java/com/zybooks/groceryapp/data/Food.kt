package com.zybooks.groceryapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zybooks.groceryapp.ui.FoodItem

@Entity
data class Food(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var foodItem: String = "", //FoodItem(), item, quantity -> will build into this when called
    var quantity: Int = 0,
    var inPantry: Int = 0,  // will be 0 or 1, one or the other
    var inGrocery: Int = 0,
)