package com.panther.shoeapp.ui.presentation.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panther.shoeapp.R

@Composable
fun OrderSuccessfulScreen() {

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
                .size(300.dp)
        )
        
        Text(
            text = "Congratulations!!",
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        )
        
        Text(
            text = "Order Done Successfully and Payment is sent for the product!",
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun SuccessPreview() {
    OrderSuccessfulScreen()
}