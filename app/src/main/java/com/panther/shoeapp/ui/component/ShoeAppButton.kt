package com.panther.shoeapp.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.panther.shoeapp.ui.theme.white

@Composable
fun ShoeAppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFF69BCFC),
        contentColor = white
    ),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        border = border,
        enabled = enabled,
        contentPadding = contentPadding,
        content = content
    )
}

@Preview
@Composable
fun PreviewButton() {

    ShoeAppButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /*TODO*/ }) {}

}