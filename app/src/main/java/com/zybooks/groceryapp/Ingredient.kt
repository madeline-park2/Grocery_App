package com.zybooks.groceryapp

data class Ingredient(
    var ingredient: FoodItem = FoodItem("", "", ""),
    var amount: String = ""
)
