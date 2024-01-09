package com.panther.shoeapp.ui.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.ui.component.AuthTextField
import com.panther.shoeapp.ui.component.GoogleButton
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.utils.Screen

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        Text(
            text = "Welcome Back!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = navyBlue
        )
        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = "Enter password to login into your account",
            color = navyBlue
        )

        Spacer(modifier = Modifier.padding(16.dp))

        GoogleButton()

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
        Spacer(modifier = Modifier.padding(16.dp))

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

        Row {
            val checkedState = remember { mutableStateOf(false ) }
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = navyBlue,
                    uncheckedColor = navyBlue
                )

            )

            Text(text = "Remember me", Modifier.padding(start = 8.dp, top = 16.dp))
        }
        
        Spacer(modifier = Modifier.padding(16.dp))
        
        ShoeAppButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(66.dp),
            onClick = {
                navController.popBackStack()
                navController.navigate(route = Screen.HomeScreen.route)
            }
        ) {
            Text(text = "Log in")
            
        }

    }
}

@Composable
fun LoginTextFields() {


}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}