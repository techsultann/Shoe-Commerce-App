package com.panther.shoeapp.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.panther.shoeapp.ui.theme.FieldColor

@Composable
fun CardButton(
    boolean: Boolean,
    icon: Int,
    onClick: () -> Unit
) {

    IconButton(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .width(81.dp)
            .height(65.dp),
        onClick = onClick,
        colors = IconButtonDefaults.outlinedIconButtonColors(
            containerColor = FieldColor
        ),
        enabled = boolean
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "card icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(40.dp)
        )
    }
}