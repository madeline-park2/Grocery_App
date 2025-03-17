package com.zybooks.groceryapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.groceryapp.data.Food

@Composable
fun PantryScreen(
    modifier: Modifier = Modifier,
    pantryScreenViewModel: PantryScreenViewModel = viewModel(
        factory = PantryScreenViewModel.Factory
    ),
    onUpClick: () -> Unit = { }
) {
    val uiState = pantryScreenViewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            GroceryAppBar(
                title = "Your Pantry",
                canNavigateBack = true,
                onUpClick = onUpClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(0.dp),
            modifier = modifier.padding(innerPadding),

        )
        {
            items(uiState.value.foodItemList.size) { count ->
                //CreateText(uiState.value.foodItemList)
                //Text(uiState.value.foodItemList[count])
                CreateText(uiState.value.foodItemList[count])
            }
        }
    }
}

