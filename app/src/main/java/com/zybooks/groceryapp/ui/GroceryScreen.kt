package com.zybooks.groceryapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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

    Scaffold(
        topBar = {
            GroceryAppBar(
                title = "Your Grocery List",
                canNavigateBack = true,
                onUpClick = onUpClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { groceryScreenViewModel.showDialog() },
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

