package com.panther.shoeapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.navigation.RootNavGraph
import com.panther.shoeapp.ui.theme.ShoeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoeAppTheme {

                RootNavGraph(navController = rememberNavController())
            }
        }
    }
}

