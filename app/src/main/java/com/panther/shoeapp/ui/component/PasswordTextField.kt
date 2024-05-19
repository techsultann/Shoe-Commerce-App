package com.panther.shoeapp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.panther.shoeapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit = {}
) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val icon = if (passwordHidden)
        painterResource(id = R.drawable.baseline_visibility)
    else
        painterResource(id = R.drawable.baseline_visibility_off)

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(66.dp),
        value = value,
        onValueChange = onValueChange,
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

    PasswordTextField(value = "Password")
}