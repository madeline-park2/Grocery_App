package com.zybooks.groceryapp.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class GroceryScreenViewModel : ViewModel() {
    val groceryList = mutableStateListOf<FoodItem>()
}