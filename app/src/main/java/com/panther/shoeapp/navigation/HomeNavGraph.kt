package com.panther.shoeapp.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.panther.shoeapp.ui.presentation.category.CategoryScreen
import com.panther.shoeapp.ui.presentation.home.HomeScreen
import com.panther.shoeapp.ui.presentation.home.HomeViewModel
import com.panther.shoeapp.utils.HOME_ROUTE
import com.panther.shoeapp.utils.Screen


fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
) {

    navigation(
        startDestination = Screen.HomeScreen.route,
        route = HOME_ROUTE
    ){
        composable(route = Screen.HomeScreen.route){
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController, viewModel)
        }
        composable(route = Screen.CategoryScreen.route){
            CategoryScreen()
        }

    }

}