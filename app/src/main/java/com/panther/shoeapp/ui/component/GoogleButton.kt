package com.panther.shoeapp.ui.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.panther.shoeapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleButton() {
    var clicked by remember { mutableStateOf(false) }
    val mContext = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(66.dp),
        onClick = {
            //clicked = !clicked
                  Toast.makeText(mContext, "Why do you like things easy?... Fill the form", Toast.LENGTH_LONG).show()
                  },
        shape = MaterialTheme.shapes.extraLarge,
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.super_g ),
                contentDescription = "Google button",
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Continue with Google",
                color = Color(0xFF152354)
            )

        }

    }
}

@Preview
@Composable
fun PreviewGoogleButton() {
    GoogleButton()
}