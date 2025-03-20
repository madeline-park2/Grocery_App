package com.zybooks.groceryapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GroceryScreen(
    modifier: Modifier = Modifier,
    groceryScreenViewModel: GroceryScreenViewModel = viewModel(
        factory = GroceryScreenViewModel.Factory
    ),
    onUpClick: () -> Unit = { }
) {
    val uiState = groceryScreenViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value.isDialogVisible) {
        AddDialog(
            onConfirmation = { food ->
                groceryScreenViewModel.hideDialog()
                groceryScreenViewModel.addGroceryItem(food[0], food[1].filterNot { it.isWhitespace() }.toInt())
            },
            onDismissRequest = {
                groceryScreenViewModel.hideDialog()
            },
        )
    }

    if (uiState.value.isDelDialogVisible) {
        DelDialog(
            onConfirmation = { food ->
                groceryScreenViewModel.hideDelDialog()
                groceryScreenViewModel.delGroceryItem(food.filterNot { it.isWhitespace() }.toInt())
            },
            onDismissRequest = {
                groceryScreenViewModel.hideDelDialog()
            },
        )
    }

    if (uiState.value.isMoveDialogVisible) {
        MoveDialog(
            onConfirmation = { food ->
                groceryScreenViewModel.hideMoveDialog()
                groceryScreenViewModel.moveGroceryItem(food.filterNot { it.isWhitespace() }.toInt())
            },
            onDismissRequest = {
                groceryScreenViewModel.hideMoveDialog()
            },
        )
    }

    Scaffold(
        topBar = {
            GroceryAppBar(
                title = "Your Grocery List",
                canNavigateBack = true,
                onUpClick = onUpClick,
                onDeleteClick = { groceryScreenViewModel.showDelDialog() },
                onMoveClick = { groceryScreenViewModel.showMoveDialog() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { groceryScreenViewModel.showDialog() },
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        },
    ) { innerPadding ->
        Grid(
            foodList = uiState.value.foodItemList,
            modifier = modifier.padding(innerPadding),
        )
    }
}

