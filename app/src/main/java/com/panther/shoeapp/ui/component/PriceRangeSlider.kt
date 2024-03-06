package com.panther.shoeapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.ui.theme.skyBlue
import com.panther.shoeapp.ui.theme.white
import java.text.NumberFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceRangeSlider(modifier: Modifier){
    var priceRange by remember { mutableStateOf(10f..100f) }

    Column {
        Text(
            modifier = modifier.padding(16.dp),
            text = "Price Range",
            color = navyBlue
        )
        val priceRangeStart = NumberFormat.getNumberInstance(
            Locale.getDefault()).format(priceRange.start)
        val priceRangeEnd = NumberFormat.getNumberInstance(
            Locale.getDefault()).format(priceRange.endInclusive)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "N${priceRangeStart}",
                color = navyBlue
            )

            Spacer(modifier = modifier.weight(1f))

            Text(
                text = "N${priceRangeEnd} ",
                color = navyBlue
            )
        }
        androidx.compose.material3.RangeSlider(
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = CircleShape,
                    spotColor = skyBlue,
                    ambientColor = Color.Transparent
                )
            ,
            value = priceRange,
            onValueChange = { range -> priceRange = range },
            valueRange = 10f..100f,
            onValueChangeFinished = {
                // launch some business logic update with the state you hold
                // viewModel.updateSelectedSliderValue(sliderPosition)
            },
            colors = SliderDefaults.colors(
                activeTrackColor = skyBlue,
                inactiveTrackColor = MaterialTheme.colorScheme.surface,
                thumbColor = white
            )
        )

    }
}

@Preview
@Composable
fun PreviewRangeSlider() {
    PriceRangeSlider(modifier = Modifier)
}