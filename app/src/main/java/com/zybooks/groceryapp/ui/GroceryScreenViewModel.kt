package com.zybooks.groceryapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.groceryapp.FoodApplication
import com.zybooks.groceryapp.data.Food
import com.zybooks.groceryapp.data.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList

class GroceryScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val foodRepo: FoodRepository
) : ViewModel() {


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FoodApplication)
                GroceryScreenViewModel(
                    savedStateHandle = createSavedStateHandle(),
                    foodRepo = application.foodRepository)
            }
        }
    }

    val uiState: StateFlow<GroceryScreenUiState> = transformedFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = GroceryScreenUiState(),
        )

    private fun transformedFlow() = combine(
        foodRepo.getAllGrocery()
    ) { foods ->
        GroceryScreenUiState(
            groceryItemList = foods[0]
        )
    }

}

data class GroceryScreenUiState(
    val groceryItemList: List<Food> = emptyList()
)