package com.panther.shoeapp.ui.component


import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.navigation.BottomBarScreen
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.ui.theme.white

@Composable
fun BottomNav(navController: NavHostController) {

    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Discovery,
        BottomBarScreen.Cart,
        BottomBarScreen.Message,
        BottomBarScreen.Profile
    )
    val navBackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackEntry?.destination
    val bottomBarDestination = screens.any { it.route == currentDestination?.route }

    if (bottomBarDestination){

        NavigationBar(
            modifier = Modifier,
            containerColor = navyBlue,
            contentColor = Color.White
        ) {

            screens.forEach { screen ->

                AddItem(
                    screen = screen,
                    currentDestination = currentDestination ,
                    navController = navController
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

        NavigationBarItem(
            modifier = Modifier
                .clip(CircleShape),
            selected = currentDestination?.hierarchy?.any {
                it.route == screen.route
            } == true,
            onClick = {
                navController.navigate(screen.route){
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = screen.icon),
                    contentDescription = "navigation icon"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = navyBlue,
                unselectedIconColor = white,
                indicatorColor = MaterialTheme.colorScheme.background
            )
        )



}

@Preview
@Composable
fun PreviewBottomNav() {

    BottomNav(navController = rememberNavController())
}