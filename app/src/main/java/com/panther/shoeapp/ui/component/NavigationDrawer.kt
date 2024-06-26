package com.panther.shoeapp.ui.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.R
import com.panther.shoeapp.navigation.Graph
import com.panther.shoeapp.navigation.HomeScreenNav
import com.panther.shoeapp.presentation.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer(
    route: String,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel()
) {

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val mContext = LocalContext.current

    ModalDrawerSheet(
        drawerContainerColor = Color.White,
        drawerContentColor = MaterialTheme.colorScheme.background
    ) {

                DrawerHeader(modifier)

                Spacer(modifier = Modifier.padding(vertical = 16.dp))

                NavigationDrawerItem(
                    label = {

                        Text(
                            text = "All Categories",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary

                            //NavigationDrawerItemColors = NavigationDrawerItemDefaults.colors(),
                        )

                    },
                    selected = route == Graph.HOME,
                    onClick = { navController.navigate(route = HomeScreenNav.CategoryScreen.route) },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.background,
                        unselectedContainerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RectangleShape,
                    modifier = Modifier.background(Color.White)
                )

                NavigationDrawerItem(
                    label = {

                        Text(
                            text = "Brands",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                    },
                    selected = route == Graph.HOME,
                    onClick = { navController.navigate( route = HomeScreenNav.BrandScreen.route) },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.background,
                        unselectedContainerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RectangleShape
                )

                NavigationDrawerItem(
                    label = {

                        Text(
                            text = "Orders",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                    },
                    selected = route == Graph.HOME,
                    onClick = { navController.navigate(route = HomeScreenNav.TrackOrder.route) },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.background,
                        unselectedContainerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RectangleShape
                )

                NavigationDrawerItem(
                    label = {

                        Text(
                            text = "Location",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                    },
                    selected = route == Graph.HOME,
                    onClick = {
                        Toast.makeText(mContext, "This feature is not implemented yet", Toast.LENGTH_SHORT).show()
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.background,
                        unselectedContainerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RectangleShape
                )

                NavigationDrawerItem(
                    label = {

                        Text(
                            text = "Payment Cards",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                    },
                    selected = route == Graph.HOME,
                    onClick = { navController.navigate(route = "${HomeScreenNav.PaymentCardScreen.route}/{cardType}/{name}/{cardNumber}") },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.background,
                        unselectedContainerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RectangleShape
                )

                NavigationDrawerItem(
                    label = {

                        Text(
                            text = "Track Order",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary

                        )

                    },
                    selected = route == Graph.HOME,
                    onClick = {
                        Toast.makeText(mContext, "This feature is not implemented yet", Toast.LENGTH_SHORT).show()
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.background,
                        unselectedContainerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RectangleShape
                )

                NavigationDrawerItem(
                    label = {

                        Text(
                            text = "Scan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                    },
                    selected = route == Graph.HOME,
                    onClick = {
                        Toast.makeText(mContext, "This feature is not implemented yet", Toast.LENGTH_SHORT).show()
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.background,
                        unselectedContainerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RectangleShape
                )

                NavigationDrawerItem(
                    label = {

                        Text(
                            text = "Settings",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                    },
                    selected = route == Graph.HOME,
                    onClick = {
                        Toast.makeText(mContext, "This feature is not implemented yet", Toast.LENGTH_SHORT).show()
                              },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.background,
                        unselectedContainerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RectangleShape
                )

                Spacer(modifier.padding(vertical = 20.dp))

                NavigationDrawerItem(
                    modifier = modifier.padding(bottom = 26.dp),
                    label = {

                        Text(
                            text = "Sign Out",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                    },
                    selected = route == Graph.HOME,
                    onClick = {
                        viewModel.logout()
                        navController.popBackStack()
                        navController.navigate(route = Graph.AUTHENTICATION)
                              },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.share),
                            contentDescription = "Sign out icon",
                            tint = Color.Unspecified
                        )

                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.background,
                        unselectedContainerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RectangleShape
                )
            }
        
    }

@Preview
@Composable
fun PreviewNavDrawer() {
    NavDrawer(route = String(), modifier = Modifier, rememberNavController())
}



@Composable
fun DrawerHeader(
    modifier: Modifier,
    viewModel: HomeViewModel = viewModel()
) {

    val displayName by viewModel.displayName.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(),
        contentAlignment = Alignment.Center
    ) {

        Row(
            horizontalArrangement = Arrangement.Start ,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            Image(
                painterResource(id = R.drawable.techsultan),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Column {

                Text(
                    text = displayName ,
                    color = Color(0xFF152354),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Edit Profile",
                    color = Color(0xFF152354),
                    fontSize = 16.sp,
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewDrawerHeader() {
    DrawerHeader(modifier = Modifier)
}



