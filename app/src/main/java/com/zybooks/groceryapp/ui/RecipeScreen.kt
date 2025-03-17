package com.zybooks.groceryapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    groceryScreenViewModel: GroceryScreenViewModel = GroceryScreenViewModel(),
    onUpClick: () -> Unit = { }
) {
    Scaffold(
        topBar = {
            GroceryAppBar(
                title = "Your Recipes",
                canNavigateBack = true,
                onUpClick = onUpClick
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(0.dp),
            modifier = modifier.padding(innerPadding)
        ) {
            /*items(viewModel.petList) { pet ->
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