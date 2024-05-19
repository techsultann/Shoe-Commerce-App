package com.panther.shoeapp.presentation.checkout

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.panther.shoeapp.R
import com.panther.shoeapp.ui.component.ShoeAppButton

@Composable
fun OrderSuccessfulScreen(
    navHostController: NavHostController,
    viewModel: CheckoutViewModel = viewModel(),
    status: String?,
    txRef: String?,
    transactionId: String?
) {

    val alpha = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        alpha.animateTo( 1f,
            animationSpec = tween(2000)
        )

        if (transactionId != null) {
            viewModel.verifyPayment(transactionId)
        }
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.successful_order),
            contentDescription = "success icon",
            modifier = Modifier
                .alpha(alpha.value)
                .size(300.dp)
        )
        
        Text(
            text = "Congratulations!!!",
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            modifier = Modifier
        )
        
        Text(
            text = "Order Done Successfully and Payment is sent for the product!",
            textAlign = TextAlign.Center,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(50.dp))

        ShoeAppButton(
            onClick = {
                navHostController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
        ) {
            Text(text = "Done", fontSize = 18.sp)
        }
    }
}

/*
@Preview
@Composable
fun SuccessPreview() {
    OrderSuccessfulScreen(rememberNavController())
}*/
