package com.panther.shoeapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.panther.shoeapp.ui.presentation.discovery.DiscoveryScreen
import com.panther.shoeapp.ui.presentation.home.HomeScreen
import com.panther.shoeapp.utils.BOTTOM_NAV_ROUTE


fun NavGraphBuilder.bottomNavGraph(
    navController: NavHostController
) {
    
     navigation(
        startDestination = BottomBarScreen.Home.route,
         route = BOTTOM_NAV_ROUTE
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }
         composable(route = BottomBarScreen.Discovery.route){
             DiscoveryScreen(navController)
         }


    }
}