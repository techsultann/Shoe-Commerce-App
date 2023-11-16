package com.panther.shoeapp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTextField(
    modifier: Modifier = Modifier.requiredHeight(66.dp),
    singleLine: Boolean = true,
    label: @Composable() (() -> Unit)?
) {

    var text by remember {
        mutableStateOf(TextFieldValue(""))
    }
    TextField(
        modifier = modifier
            .clip(CircleShape)
            .fillMaxWidth()
            .clipToBounds(),
        value = text,
        onValueChange = { it ->
            text = it
        },
        singleLine = singleLine,
        label = label,
        shape = CircleShape
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewAuthTextField() {
    AuthTextField(
        label = { /*TODO*/ }
    )
}