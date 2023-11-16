package com.panther.shoeapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panther.shoeapp.R
import com.panther.shoeapp.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer(
    route: String,
     modifier: Modifier = Modifier
) {

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)

    }

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
                    selected = route == Screen.HomeScreen.route,
                    onClick = { /*TODO*/ },
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
                    selected = route == Screen.HomeScreen.route,
                    onClick = { /*TODO*/ },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.background,
                        unselectedContainerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RectangleShape
                )

                NavigationDrawerItem(
                    label = {

                        Text(
                            text = "Discover All",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                    },
                    selected = route == Screen.HomeScreen.route,
                    onClick = { /*TODO*/ },
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
                    selected = route == Screen.HomeScreen.route,
                    onClick = { /*TODO*/ },
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
                    selected = route == Screen.HomeScreen.route,
                    onClick = { /*TODO*/ },
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
                    selected = route == Screen.HomeScreen.route,
                    onClick = { /*TODO*/ },
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
                    selected = route == Screen.HomeScreen.route,
                    onClick = { /*TODO*/ },
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
                    selected = route == Screen.HomeScreen.route,
                    onClick = { /*TODO*/ },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.background,
                        unselectedContainerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RectangleShape
                )

                Spacer(modifier.weight(1f))

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
                    selected = route == Screen.HomeScreen.route,
                    onClick = { /*TODO*/ },
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
    NavDrawer(route = String())
}



@Composable
fun DrawerHeader(modifier: Modifier) {

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
                    text = "Alexander Hussain",
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



