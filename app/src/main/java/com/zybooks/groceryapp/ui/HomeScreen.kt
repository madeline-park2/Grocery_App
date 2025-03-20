package com.zybooks.groceryapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
                title = "Home",
                onDeleteClick = {}
            )
        }
    ) { innerPadding ->
        LazyColumn (
            //columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(20.dp),
            modifier = modifier.padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            items(2) { index ->
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = { onButtonClick(index) },
                        modifier = modifier,

                        ) {
                        when (index) {
                            0 -> Text("Your Pantry")
                            1 -> Text("Your Groceries")
                            //2 -> Text("Your Recipes")
                        }

                    }
                }

            }
        }
    }
}
