package com.panther.shoeapp.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.navigation.AuthScreen
import com.panther.shoeapp.ui.component.AuthTextField
import com.panther.shoeapp.ui.component.GoogleButton
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel = viewModel()
) {
    val mContext = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    val signUpResult by viewModel.signupResult.collectAsState()
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var showProgressBar by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    var buttonClicked by rememberSaveable { mutableStateOf(false) }

    val validateForm = email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()
            && confirmPassword.isNotEmpty() && password == confirmPassword


    LaunchedEffect(key1 = signUpResult) {

        when (signUpResult) {

            is Resource.Loading -> {
                showProgressBar = true
            }
            is Resource.Success -> {
                navController.popBackStack()
                navController.navigate(route = AuthScreen.LoginScreen.route)
            }
            is Resource.Error -> {
                Toast.makeText(mContext, signUpResult.message, Toast.LENGTH_SHORT).show()
               snackBarHostState.showSnackbar(signUpResult.message?: "An unknown error occurred.")
            }
            is Resource.Idle -> {

            }
        }
    }

    Scaffold(

        topBar = {
            TopAppBar(
                title = {

                },
                navigationIcon = {

                },
                actions = {

                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())

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
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                trailingIcon = {},
                visualTransformation = VisualTransformation.None,
                supportingText = {
                    if (buttonClicked && username.isEmpty()) Text(text = "This field cannot be empty")
                },
                isError = buttonClicked && username.isEmpty()
            )

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
                    if (buttonClicked && email.isEmpty()) Text(text = "This field cannot be empty")
                },
                isError = buttonClicked && email.isEmpty()
            )

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

            Text(text = "Confirm Password")

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            AuthTextField(
                label = {
                    Text(text = "Confirm your password")
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


            Spacer(modifier = Modifier.weight(1f))

            ShoeAppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(66.dp),
                onClick = {
                    buttonClicked = true
                    if (validateForm) {
                        viewModel.signUp(email, password, username)
                    }

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
                    .padding(bottom = 8.dp)
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
    


}



@Preview
@Composable
fun PreviewSignUpScreen(){
    Column(modifier = Modifier.fillMaxSize()) {

        SignupScreen(navController = rememberNavController())
    }
}
