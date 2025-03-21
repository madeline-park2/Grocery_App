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

class GroceryScreenViewModel(
    private val foodRepo: FoodRepository
) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FoodApplication)
                GroceryScreenViewModel(
                    foodRepo = application.foodRepository)
            }
        }
    }

    private val isDialogVisible = MutableStateFlow(false)
    private val isDelDialogVisible = MutableStateFlow(false)
    private val isMoveDialogVisible = MutableStateFlow(false)

    val uiState: StateFlow<GroceryScreenUiState> = transformedFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = GroceryScreenUiState(),
        )

    private fun transformedFlow() = combine(
        foodRepo.getAllGrocery(),
        isDialogVisible,
        isDelDialogVisible,
        isMoveDialogVisible
    ) { foods, dialogVisible, dec, m ->
        GroceryScreenUiState(
            foodItemList = foods,
            isDialogVisible = dialogVisible,
            isDelDialogVisible = dec,
            isMoveDialogVisible = m
        )
    }

    fun addGroceryItem(item: String, quantity: Int) {
        foodRepo.addFood(Food(foodItem = item, quantity = quantity, inPantry = 0, inGrocery = 1))
    }

    fun delGroceryItem(index: Int) {
        foodRepo.deleteFood(uiState.value.foodItemList[index])
    }

    fun moveGroceryItem(index: Int) {
        val x = uiState.value.foodItemList[index]
        foodRepo.addFood(Food(foodItem = x.foodItem, quantity = x.quantity, inPantry = 1, inGrocery = 0))
        foodRepo.deleteFood(x)
    }

    fun showDialog() {
        isDialogVisible.value = true
    }

    fun hideDialog() {
        isDialogVisible.value = false
    }

    fun showDelDialog() {
        isDelDialogVisible.value = true
    }

    fun hideDelDialog() {
        isDelDialogVisible.value = false
    }

    fun showMoveDialog() {
        isMoveDialogVisible.value = true
    }

    fun hideMoveDialog() {
        isMoveDialogVisible.value = false
    }

}

data class GroceryScreenUiState(
    val foodItemList: List<Food> = emptyList(),
    val isDelDialogVisible: Boolean = false,
    val isDialogVisible: Boolean = false,
    val isMoveDialogVisible: Boolean = false
)