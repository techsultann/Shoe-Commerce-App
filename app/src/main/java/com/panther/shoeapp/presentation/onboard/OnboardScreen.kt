package com.panther.shoeapp.presentation.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.R
import com.panther.shoeapp.navigation.AuthScreen
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.ui.theme.secondaryTextColor

@Composable
fun OnboardScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {

        Box(
            modifier = Modifier
                .padding(start = 32.dp, top = 52.dp)
                .fillMaxWidth()
                .height(300.dp)
        ){

            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Nike",
                fontSize = 180.sp,
                color = secondaryTextColor.copy(alpha = 0.2f)
            )
            Image(
                painter = painterResource(id = R.drawable.air_zoom),
                contentDescription = "nike shoe image",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 22.dp)
                    .size(300.dp)
                    .scale(scaleX = -1f, scaleY = 1f),
                contentScale = ContentScale.
                Fit
            )
        }

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "The",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace ,
            style = TextStyle.Default.copy(
                fontSize = 64.sp,
                drawStyle = Stroke(
                    miter = 10f,
                    width = 5f,
                    join = StrokeJoin.Round
                ),
                color = navyBlue
            )
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Awesome",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace ,
            color = navyBlue,
            fontSize = 64.sp
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Branded",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace ,
            style = TextStyle.Default.copy(
                fontSize = 64.sp,
                drawStyle = Stroke(
                    miter = 2f,
                    width = 5f,
                    join = StrokeJoin.Round
                ),
                color = navyBlue
            )
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Shoes",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace ,
            color = navyBlue,
            fontSize = 64.sp
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Enjoy your vacation with our super comfy shoes",
            fontFamily = FontFamily.Monospace ,
            color = navyBlue,
            fontSize = 20.sp
        )

        ShoeAppButton(
            onClick = {
                navController.popBackStack()
                navController.navigate(route = AuthScreen.LoginScreen.route)
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(66.dp)
        ) {
            Text(
                text = "Get Started",
                fontSize = 20.sp
            )
            
        }


        }


}

@Preview
@Composable
fun PreviewOnboardScreen() {

    OnboardScreen(navController = rememberNavController())
}