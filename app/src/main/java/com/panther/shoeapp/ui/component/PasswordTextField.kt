package com.panther.shoeapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.panther.shoeapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(

    modifier: Modifier = Modifier
) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val icon = if (passwordHidden)
        painterResource(id = R.drawable.baseline_visibility)
    else
        painterResource(id = R.drawable.baseline_visibility_off)

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = { password = it },
        singleLine = true,
        label = { Text("Enter password") },
        trailingIcon = {
            IconButton(
                onClick = { passwordHidden = !passwordHidden
                }) {

                Icon(
                    painter = icon,
                    contentDescription = "Visibility icon")

            }
        },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

        )
}

@Preview
@Composable
fun PreviewPasswordTextField() {

    PasswordTextField()
}