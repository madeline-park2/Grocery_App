package com.zybooks.groceryapp.ui

data class Ingredient(
    var ingredient: FoodItem = FoodItem("", "", ""),
    var amount: String = ""
)
