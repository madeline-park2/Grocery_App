package com.zybooks.groceryapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.groceryapp.FoodApplication
import com.zybooks.groceryapp.data.Food
import com.zybooks.groceryapp.data.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class PantryScreenViewModel(
    private val foodRepo: FoodRepository
    ) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FoodApplication)
                PantryScreenViewModel(
                    foodRepo = application.foodRepository)
            }
        }
    }

    private val isDialogVisible = MutableStateFlow(false)
    private val decCount = MutableStateFlow(false)

    val uiState: StateFlow<PantryScreenUiState> = transformedFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = PantryScreenUiState(),
        )

    private fun transformedFlow() = combine(
        foodRepo.getAllPantry(),
        isDialogVisible,
        decCount
    ) { foods, dialogVisible, dec ->
        PantryScreenUiState(
            foodItemList = foods,
            isDialogVisible = dialogVisible,
            decCount = dec
        )
    }
    fun addPantryItem(item: String, quantity: Int) {
        foodRepo.addFood(Food(foodItem = item, quantity = quantity, inPantry = 1, inGrocery = 0))
    }

    fun getItem(ind: Long) {
        foodRepo.getPantryItem(ind)
    }

    fun decPantryItem(food: Food) {
        foodRepo.deleteFood(food)
    }

    fun showDialog() {
        isDialogVisible.value = true
    }

    fun hideDialog() {
        isDialogVisible.value = false
    }

}

data class PantryScreenUiState(
    val foodItemList: List<Food> = emptyList(),
    val isDialogVisible: Boolean = false,
    val decCount: Boolean = false
)