package com.zybooks.groceryapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(0.dp),
            modifier = modifier.padding(innerPadding),

        )
        {
            items(uiState.value.foodItemList.size) {
                CreateText(uiState.value.foodItemList)
            }

            /*items(pantryScreenViewModel.food) { pet ->
                Image(
                    painter = painterResource(id = pet.imageId),
                    contentDescription = "${pet.type} ${pet.gender}",
                    modifier = Modifier.clickable(
                        onClick = { onImageClick(pet) },
                        onClickLabel = "Select the pet"
                    )
                ) */
        }
    }
}

@Composable
fun CreateText(stringList: List<Food>) {
    stringList.forEach { item ->
        val textField = Text(item.foodItem)
    }
}
