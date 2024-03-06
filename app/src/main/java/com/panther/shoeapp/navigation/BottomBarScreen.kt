package com.panther.shoeapp.navigation

import com.panther.shoeapp.R

sealed class BottomBarScreen(
    val route: String,
    val icon: Int
) {
    object Home: BottomBarScreen(
        route = "home",
        icon = R.drawable.home
    )
    object Discovery: BottomBarScreen(
        route = "discovery",
        icon = R.drawable.discovery
    )
    object Cart: BottomBarScreen(
        route = "cart",
        icon = R.drawable.cart,
    )
    object Message: BottomBarScreen(
        route = "message",
        icon = R.drawable.heart
    )
    object Profile: BottomBarScreen(
        route = "profile",
        icon = R.drawable.profile
    )
}