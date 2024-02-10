package com.panther.shoeapp.ui.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.navigation.AuthScreen
import com.panther.shoeapp.ui.component.AuthTextField
import com.panther.shoeapp.ui.component.GoogleButton
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel = viewModel()
) {
    val vm = hiltViewModel<OnboardingViewModel>()
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    val signUpResult by viewModel.signupResult.collectAsState()
    var showProgress by remember { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(key1 = signUpResult) {
        when (signUpResult) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                showProgress = false
                viewModel.signUp(email, password, username)
                navController.popBackStack()
                navController.navigate(route = AuthScreen.LoginScreen.route)
            }
            is Resource.Error -> {
                showProgress = false
                scaffoldState.snackbarHostState.showSnackbar("Sign up failed, Please try again later")
            }
        }
    }
    
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions()
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions()
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions()
        )


        Spacer(modifier = Modifier.weight(1f))

        ShoeAppButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(66.dp),
            onClick = {
                viewModel.signUp(email, password, username)
            }
        ) {
            if (signUpResult is Resource.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surface,
                    strokeCap = StrokeCap.Butt
                )
            }
            else {
                Text(
                    text = "Sign Up",
                    fontSize = 18.sp
                )
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clickable { navController.navigate(AuthScreen.LoginScreen.route) },
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {

                append("Already have an account? ")
                append(
                    AnnotatedString(
                        text = "Sign in ",
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
fun PreviewSignUpScreen(){
    Column(modifier = Modifier.fillMaxSize()) {

        SignupScreen(navController = rememberNavController())
    }
}
