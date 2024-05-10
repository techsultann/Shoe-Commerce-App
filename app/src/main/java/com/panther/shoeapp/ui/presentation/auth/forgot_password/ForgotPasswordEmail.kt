package com.panther.shoeapp.ui.presentation.auth.forgot_password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
fun ForgotPassword(
    navController: NavHostController
) {
    var email by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        Text(
            text = "Forgot your Password?",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = navyBlue
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = "Enter your email address we will send you an email!",
            fontSize = 20.sp,
            color = navyBlue
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(text = "Email", color = navyBlue)

        Spacer(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp))

        AuthTextField(
            label = {
                Text(text = "Email")
            },
            value = email,
            onValueChange = { it ->
                email = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            },
            trailingIcon = {},
            visualTransformation = VisualTransformation.None,
            supportingText = {
                if (email.isEmpty()) Text(text = "This field cannot be empty")
            },
            isError = email.isEmpty()
        )

        Spacer(modifier = Modifier.padding(16.dp))

        ShoeAppButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(66.dp),
            onClick = {
                navController.popBackStack()
                navController.navigate(route = AuthScreen.ForgotPassword.route)
            }
        ) {
            Text(text = "Send Email")

        }



    }
}

@Preview
@Composable
fun PreviewForgotPasswordEmail() {
    ForgotPassword(navController = rememberNavController())
}
