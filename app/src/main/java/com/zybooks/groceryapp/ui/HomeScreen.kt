package com.zybooks.groceryapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = HomeScreenViewModel(),
    onButtonClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            GroceryAppBar(
                title = "Home"
            )
        }
    ) { innerPadding ->
        LazyColumn (
            //columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(0.dp),
            modifier = modifier.padding(innerPadding)
        ) {
            items(3) { index ->
                Button(
                    onClick = { onButtonClick(index) }
                ) {
                    when (index) {
                        0 -> Text("Your Pantry")
                        1 -> Text("Your Groceries")
                        2 -> Text("Your Recipes")
                    }

                }
            }
        }
    }
}
