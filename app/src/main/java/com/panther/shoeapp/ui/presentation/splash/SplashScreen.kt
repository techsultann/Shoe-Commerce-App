package com.panther.shoeapp.ui.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.navigation.AuthScreen
import com.panther.shoeapp.ui.theme.skyBlue
import com.panther.shoeapp.ui.theme.white
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navHostController: NavHostController
) {
    val alpha = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        alpha.animateTo( 1f,
            animationSpec = tween(2000)
        )
        delay(3000)
        navHostController.popBackStack()
        navHostController.navigate(route = AuthScreen.OnboardScreen.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(skyBlue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(400.dp))

       Text(
           text = "FLIP",
           fontSize = 36.sp,
           fontFamily = FontFamily.Monospace,
           fontStyle = FontStyle.Italic,
           color = white,
           fontWeight = FontWeight.Bold
       )

        Text(
            text = "Discover all trendy shoes",
            fontSize = 18.sp,
            fontFamily = FontFamily.Monospace,
            color = white
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Powered by AppShark",
            fontSize = 18.sp,
            fontFamily = FontFamily.Monospace,
            color = white,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewSplashScreen(){
    SplashScreen(navHostController = rememberNavController())
}