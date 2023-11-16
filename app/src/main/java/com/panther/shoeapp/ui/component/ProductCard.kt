package com.panther.shoeapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panther.shoeapp.R
import com.panther.shoeapp.ui.theme.FieldColor

@Composable
fun ProductCard(name: String, price: String, image: Int) {
    Column(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .width(170.dp)
                .height(210.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(FieldColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "sneaker image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(200.dp)
            )

        }
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        Text(
            text = name,
            color = Color(0xFF152354),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        Text(
            text = price,
            color = Color(0xFF152354),
            fontSize = 20.sp
        )

    }
}

@Preview
@Composable
fun ProductCardBPreview() {
    ProductCard(
        name = "Air Zoom",
        price = "$ 650",
        image = R.drawable.air_zoom_250
    )
}