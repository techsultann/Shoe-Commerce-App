package com.panther.shoeapp.ui.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.panther.shoeapp.navigation.AuthScreen
import com.panther.shoeapp.navigation.Graph
import com.panther.shoeapp.ui.component.AuthTextField
import com.panther.shoeapp.ui.component.GoogleButton
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel = viewModel()
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(key1 = loginState) {
        val auth = Firebase.auth
        val currentUser = auth.currentUser

        when (loginState) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                if (currentUser != null) {
                    navController.popBackStack()
                    navController.navigate(route = Graph.HOME)
                }

            }
            is Resource.Error -> {
                scaffoldState.snackbarHostState.showSnackbar("Login failed")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(36.dp))

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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions()
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions()
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

        Spacer(modifier = Modifier.weight(1f))
        
         ShoeAppButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(66.dp),
            onClick = {
                viewModel.login(email, password)
            }
        ) {
             if (loginState is Resource.Loading) {
                 Text(
                     text = "Login",
                     fontSize = 18.sp
                 )
             }
             else {
                 CircularProgressIndicator(
                     color = MaterialTheme.colorScheme.primary,
                     trackColor = MaterialTheme.colorScheme.surface,
                     strokeCap = StrokeCap.Butt
                 )

             }
            
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clickable { navController.navigate(AuthScreen.SignupScreen.route) },
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {

                append("Don't have an account? ")
                append(
                    AnnotatedString(
                        text = "Sign up ",
                        spanStyle = SpanStyle(Color.Red)
                    )
                )
            },
            fontSize = 16.sp
        )

    }
}


@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}