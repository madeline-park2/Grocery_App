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
    private val isDelDialogVisible = MutableStateFlow(false)
    private val isMoveDialogVisible = MutableStateFlow(false)

    val uiState: StateFlow<PantryScreenUiState> = transformedFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = PantryScreenUiState(),
        )

    private fun transformedFlow() = combine(
        foodRepo.getAllPantry(),
        isDialogVisible,
        isDelDialogVisible,
        isMoveDialogVisible
    ) { foods, dialogVisible, dec, m ->
        PantryScreenUiState(
            foodItemList = foods,
            isDialogVisible = dialogVisible,
            isDelDialogVisible = dec,
            isMoveDialogVisible = m
        )
    }
    fun addPantryItem(item: String, quantity: Int) {
        foodRepo.addFood(Food(foodItem = item, quantity = quantity, inPantry = 1, inGrocery = 0))
    }

    fun movePantryItem(index: Int) {
        val x = uiState.value.foodItemList[index]
        foodRepo.addFood(Food(foodItem = x.foodItem, quantity = x.quantity, inPantry = 0, inGrocery = 1))
        foodRepo.deleteFood(x)
    }

    fun delPantryItem(index: Int) {
        foodRepo.deleteFood(uiState.value.foodItemList[index])
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

data class PantryScreenUiState(
    val foodItemList: List<Food> = emptyList(),
    val isDialogVisible: Boolean = false,
    val isDelDialogVisible: Boolean = false,
    val isMoveDialogVisible: Boolean = false
)