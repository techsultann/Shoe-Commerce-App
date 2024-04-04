package com.panther.shoeapp.navigation

import com.panther.shoeapp.R

sealed class BottomBarScreen(
    val route: String,
    val icon: Int,
    val label: String
) {
    object Home: BottomBarScreen(
        route = "home",
        icon = R.drawable.home,
        label = "Home"
    )
    object Discovery: BottomBarScreen(
        route = "discovery",
        icon = R.drawable.discovery,
        label = "Discovery"
    )
    object Cart: BottomBarScreen(
        route = "cart",
        icon = R.drawable.cart,
        label = "Cart"
    )
    object Message: BottomBarScreen(
        route = "message",
        icon = R.drawable.heart,
        label = "Messages"
    )
    object Profile: BottomBarScreen(
        route = "profile",
        icon = R.drawable.profile,
        label = "Profile"
    )
}