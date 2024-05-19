package com.panther.shoeapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panther.shoeapp.ui.theme.FieldColor

@Composable
fun DetailsBackground(
    text: String,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .height(65.dp)
            .fillMaxWidth(),
        color = FieldColor,
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(FieldColor)
                    .clip(CircleShape)
            )

            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
                    .clickable { onClick() },
                tint = Color.Black
            )
        }

    }
}

//@Preview
//@Composable
//fun PreviewDetailsBackground() {
//    DetailsBackground(text = "Profile'", imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos)
//}