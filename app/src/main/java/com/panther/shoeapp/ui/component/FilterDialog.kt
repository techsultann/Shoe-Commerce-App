package com.panther.shoeapp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.ui.theme.skyBlue
import com.panther.shoeapp.ui.theme.white


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RangeSlider(modifier: Modifier){
    var sliderPosition by remember { mutableStateOf(10f..100f) }

    Column {
        Text(
            modifier = modifier.padding(16.dp),
            text = "Price Range",
            color = navyBlue
        )

        androidx.compose.material3.RangeSlider(
            modifier = modifier.padding(16.dp),
            value = sliderPosition,
            onValueChange = { range -> sliderPosition = range },
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
        Text(
            modifier = modifier.padding(16.dp),
            text = sliderPosition.toString()
        )
    }
}

@Preview
@Composable
fun PreviewRangeSlider() {
    RangeSlider(modifier = Modifier)
}