package com.panther.shoeapp.ui.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.ui.component.AuthTextField
import com.panther.shoeapp.ui.component.GoogleButton
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel = viewModel()
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Create an Account",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF152354)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = "Enter email address to create an account",
            fontSize = 22.sp,
            color = Color(0xFF152354)
        )

        Spacer(modifier = Modifier.padding(16.dp))
        
        GoogleButton()
        
        Spacer(modifier = Modifier.padding(16.dp))

        Text(text = "Username")

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        AuthTextField(
            label = {
                Text(text = "Enter your username")
            },
            value = username,
            onValueChange = { it ->
                username = it
            },
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(text = "Email")

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        AuthTextField(
            label = {
                Text(text = "Enter your email")
            },
            value = email,
            onValueChange = { it ->
                email = it
            },
        )
        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        Text(text = "Password")

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        AuthTextField(
            label = {
                Text(text = "Enter your password")
            },
            value = password,
            onValueChange = { it ->
                password = it
            },
        )

        Spacer(modifier = Modifier.padding(16.dp))

        ShoeAppButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(66.dp),
            onClick = {
                viewModel.signUp(email, password, username)
                navController.popBackStack()
                navController.navigate(route = Screen.LoginScreen.route)
            }
        ) {

            Text(text = "Sign in")
        }
        

        
    }

}



@Preview
@Composable
fun PreviewSignUpScreen(){
    Column(modifier = Modifier.fillMaxSize()) {

        SignupScreen(navController = rememberNavController())
    }
}
