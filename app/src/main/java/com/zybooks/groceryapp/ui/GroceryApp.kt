package com.zybooks.groceryapp.ui

import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.zybooks.groceryapp.data.Food
import com.zybooks.groceryapp.data.FoodRepository
import kotlinx.serialization.Serializable


sealed class Routes {
    @Serializable
    data object Home

    @Serializable
    data object Pantry

    @Serializable
    data object Grocery

    @Serializable
    data object Recipe

}

@Composable
fun GroceryApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Home
    ) {
        composable<Routes.Home> {
            HomeScreen(
                onButtonClick = { index ->
                    navController.navigate(
                        when (index) {
                            0 -> Routes.Pantry
                            1 -> Routes.Grocery
                            2 -> Routes.Recipe
                            else -> Routes.Home
                        }
                    )
                }
            )
        }
        composable<Routes.Grocery> { backstackEntry ->
            val details: Routes.Grocery = backstackEntry.toRoute()

            GroceryScreen(
                onUpClick = {
                    navController.navigateUp()
                }
            )
        }
        composable<Routes.Pantry> { backstackEntry ->
            val details: Routes.Pantry = backstackEntry.toRoute()

            PantryScreen(
                onUpClick = {
                    navController.navigateUp()
                }
            )
        }
        /*composable<Routes.Recipe> { backstackEntry ->
            val details: Routes.Recipe = backstackEntry.toRoute()

            RecipeScreen(
                onUpClick = {
                    navController.navigateUp()
                }
            )
        } */
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceryAppBar(
    title: String,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = false,
    onUpClick: () -> Unit = { },
    onDeleteClick: () -> Unit,
    onMoveClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onUpClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                }
            }
        },
        actions = {
            IconButton(onClick = onDeleteClick) {
                if (title != "Home") {
                    Icon(Icons.Filled.Delete, "Delete")
                }
            }
            IconButton(onClick = onMoveClick) {
                if (title != "Home") {
                    Icon(Icons.Filled.Share, "Move")
                }
            }
        }

    )
}

@Composable
fun CreateText(
    s: Food,
    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        /*colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant */
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(s.foodItem,
                    modifier = modifier.padding(start = 12.dp))
                Text(s.quantity.toString(),
                    modifier = modifier.padding(end = 12.dp))

            }

        }
    }

@Composable
fun AddDialog(
    onConfirmation: (List<String>) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var subject by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            TextField(
                label = { Text("Food item, quantity:") },
                value = subject,
                onValueChange = { subject = it },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onConfirmation(subject.split(","))
                    }
                ),
            )
        },
        confirmButton = {
            Button(
                /*colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ), */
                onClick = {
                    onConfirmation(subject.split(","))
                }) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            Button(
                /*colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ), */
                onClick = {
                    onDismissRequest()
                }) {
                Text(text = "Cancel")
            }
        },
    )
}

@Composable
fun DelDialog(
    onConfirmation: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var subject by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            TextField(
                label = { Text("Index?") },
                value = subject,
                onValueChange = { subject = it },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onConfirmation(subject)
                    }
                ),
            )
        },
        confirmButton = {
            Button(
                /*colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ), */
                onClick = {
                    onConfirmation(subject)
                }) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            Button(
                /*colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ), */
                onClick = {
                    onDismissRequest()
                }) {
                Text(text = "Cancel")
            }
        },
    )
}

@Composable
fun MoveDialog(
    onConfirmation: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var subject by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            TextField(
                label = { Text("Index?") },
                value = subject,
                onValueChange = { subject = it },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onConfirmation(subject)
                    }
                ),
            )
        },
        confirmButton = {
            Button(
                /*colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ), */
                onClick = {
                    onConfirmation(subject)
                }) {
                Text(text = "Move")
            }
        },
        dismissButton = {
            Button(
                /*colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ), */
                onClick = {
                    onDismissRequest()
                }) {
                Text(text = "Cancel")
            }
        },
    )
}

@Composable
fun Grid(
    foodList: List<Food>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.FixedSize(512.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
    ) {
        items(foodList, key = { it.id }) { f ->
            CreateText(f)
        }
    }
}
