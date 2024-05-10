package com.panther.shoeapp.ui.presentation.auth.forgot_password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.navigation.AuthScreen
import com.panther.shoeapp.ui.component.AuthTextField
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.theme.navyBlue

@Composable
fun ForgotPasswordPass(navHostController: NavHostController) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var buttonClicked by rememberSaveable { mutableStateOf(false) }

    val validateForm = password.isNotEmpty() && confirmPassword.isNotEmpty()
            && password == confirmPassword

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        Text(
            text = "Reset Password",
            fontSize = 30.sp,
            color = navyBlue
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = "Keep a password to easily login to your account",
            fontSize = 20.sp,
            color = navyBlue
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(text = "Password", color = navyBlue)

        AuthTextField(
            label = {
                Text(text = "Enter your password")
            },
            value = password,
            onValueChange = { it ->
                password = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(onClick = {passwordHidden = !passwordHidden}
                ) {
                    val visibilityIcon = if (passwordHidden) Icons.Outlined.Visibility
                    else Icons.Outlined.VisibilityOff

                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(imageVector = visibilityIcon, contentDescription = description, tint = Color.LightGray)
                }
            },
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            supportingText = {
                if (buttonClicked && password.isEmpty()) Text(text = "This field cannot be empty")
            },
            isError = buttonClicked && password.isEmpty()
        )

        AuthTextField(
            label = {
                Text(text = "Enter your password")
            },
            value = confirmPassword,
            onValueChange = { it ->
                confirmPassword = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(onClick = {passwordHidden = !passwordHidden}
                ) {
                    val visibilityIcon = if (passwordHidden) Icons.Outlined.Visibility
                    else Icons.Outlined.VisibilityOff

                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(imageVector = visibilityIcon, contentDescription = description, tint = Color.LightGray)
                }
            },
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            supportingText = {
                when {
                    confirmPassword.isEmpty() -> Text(text = "This field cannot be empty")
                    confirmPassword != password -> Text(text = "Passwords do not match")
                }
            },
            isError = buttonClicked && confirmPassword.isEmpty() || buttonClicked && confirmPassword != password
        )

        Spacer(modifier = Modifier.padding(16.dp))

        ShoeAppButton(
            onClick = {
                buttonClicked = true

                if (validateForm) {
                    navHostController.popBackStack()
                    navHostController.navigate(route = AuthScreen.LoginScreen.route)
                }

                      },
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)

        ) {

            Text(text = "Done")
            
        }

    }
}

@Preview
@Composable
fun PreviewForgotPass() {

    ForgotPasswordPass(rememberNavController())
}