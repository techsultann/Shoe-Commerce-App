package com.panther.shoeapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.panther.shoeapp.navigation.HomeScreenNav
import com.panther.shoeapp.utils.Constants.toCurrency
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCard(
    name: String?,
    price: Double?,
    image: String?,
    shoeId: String?,
    navHostController: NavHostController
) {

    Card(
        onClick = {
            navHostController.navigate("${HomeScreenNav.DetailsScreen.route}/$shoeId")
        },
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .height(210.dp)
            .width(170.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {

        Column() {
            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.FillBounds
            )

            Text(
                text = name!!,
                modifier = Modifier
                    .padding(start = 6.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            val formattedText = formatToCurrency(amount = price!!)
            Text(
                text = price.toCurrency(),
                modifier = Modifier
                    .padding(start = 6.dp)
            )
        }
    }
}

@Composable
fun formatToCurrency(amount: Double) : String {
    val numberFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault())

    return numberFormatter.format(amount)
}