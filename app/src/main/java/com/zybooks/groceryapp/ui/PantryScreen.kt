package com.zybooks.groceryapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.groceryapp.data.FoodRepository

@Composable
fun PantryScreen(
    modifier: Modifier = Modifier,
    pantryScreenViewModel: PantryScreenViewModel = viewModel(
        factory = PantryScreenViewModel.Factory
    ),
    onUpClick: () -> Unit = { }
) {
    val uiState = pantryScreenViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value.isDialogVisible) {
        AddDialog(
            onConfirmation = { food ->
                pantryScreenViewModel.hideDialog()
                pantryScreenViewModel.addPantryItem(food[0], food[1].filterNot { it.isWhitespace() }.toInt())
            },
            onDismissRequest = {
                pantryScreenViewModel.hideDialog()
            },
        )
    }

    if (uiState.value.isDelDialogVisible) {
        DelDialog(
            onConfirmation = { food ->
                pantryScreenViewModel.hideDelDialog()
                pantryScreenViewModel.delPantryItem(food.filterNot { it.isWhitespace() }.toInt())
            },
            onDismissRequest = {
                pantryScreenViewModel.hideDelDialog()
            },
        )
    }

    Scaffold(
        topBar = {
            GroceryAppBar(
                title = "Your Pantry",
                canNavigateBack = true,
                onUpClick = onUpClick,
                onDeleteClick = { pantryScreenViewModel.showDelDialog() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { pantryScreenViewModel.showDialog() },
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    ) { innerPadding ->
            Grid(
                foodList = uiState.value.foodItemList,
                modifier = modifier.padding(innerPadding)
            )
    }
}

