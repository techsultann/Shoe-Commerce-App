package com.panther.shoeapp.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.panther.shoeapp.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable() (RowScope.() -> Unit)
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    CenterAlignedTopAppBar(
        modifier = Modifier.padding(16.dp),
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior
    )

}

@Preview
@Composable
fun PreviewTopAppBar() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = {
            Text(
                text = "Home",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF152354)

            )
        },
        navigationIcon = {
            Surface(
                modifier = Modifier
                    .size(height = 56.dp, width = 56.dp)
                    .clip(shape = CircleShape),
                tonalElevation = 4.dp,
                shadowElevation = 4.dp
            ) {
                IconButton(
                    onClick = { scope.launch {
                        drawerState.open()
                    }
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.apps_rectangle),
                        contentDescription = "navigation icon",
                        tint = Color.Unspecified
                    )
                }

            }
        },
        actions = {
            Surface(
                modifier = Modifier.size(height = 44.dp, width = 84.dp),
                shape = MaterialTheme.shapes.extraLarge,
                tonalElevation = 4.dp,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(6.dp)
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.light),
                            contentDescription = "light theme icon",
                            tint = Color.Unspecified)

                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.half__moon),
                            contentDescription = "dark theme icon",
                            tint = Color.Unspecified
                        )

                    }
                }

            }
        }
    )
}