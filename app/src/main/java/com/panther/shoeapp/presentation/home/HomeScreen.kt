package com.panther.shoeapp.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.navigation.HomeNavGraph
import com.panther.shoeapp.presentation.cart.CartViewModel
import com.panther.shoeapp.ui.component.BottomNav

@Composable
fun HomeScreen(navHostController: NavHostController = rememberNavController()) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            val viewModel = hiltViewModel<CartViewModel>()
            BottomNav(navController = navHostController, viewModel)
        },
        content = { padding ->
            HomeNavGraph(
                modifier = Modifier.padding(padding),
                navController = navHostController,
            )
        }
    )
}