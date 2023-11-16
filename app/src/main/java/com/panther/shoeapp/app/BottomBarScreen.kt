package com.panther.shoeapp.app

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
    object Favorite: BottomBarScreen(
        route = "favorite",
        icon = R.drawable.heart
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