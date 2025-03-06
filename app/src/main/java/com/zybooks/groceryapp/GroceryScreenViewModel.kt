package com.zybooks.groceryapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class GroceryScreenViewModel : ViewModel() {
    val groceryList = mutableStateListOf<FoodItem>()
}