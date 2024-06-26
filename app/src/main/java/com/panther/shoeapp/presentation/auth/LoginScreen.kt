package com.panther.shoeapp.presentation.auth

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
    var showProgressBar by rememberSaveable { mutableStateOf(false) }
    val mContext = LocalContext.current
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var buttonClicked by rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()

    val validateForm = email.isNotEmpty() && password.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
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

      //  Spacer(modifier = Modifier.padding(vertical = 4.dp))

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

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Password")

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

        Text(
            text = "Forgot Password?",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.End)
                .clickable {
                    navController.navigate(route = AuthScreen.ForgotPasswordEmail.route)
                },
            textAlign = TextAlign.End
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
                buttonClicked = true
                if (validateForm) {
                    viewModel.login(email, password)
                }

            }
        ) {
             when (loginState) {

                 is Resource.Loading -> {
                     //showProgressBar = true
                     CircularProgressIndicator(
                         color = MaterialTheme.colorScheme.primary,
                         trackColor = MaterialTheme.colorScheme.surface,
                         strokeCap = StrokeCap.Butt
                     )
                 }
                 is Resource.Success -> {

                     navController.popBackStack()
                     navController.navigate(route = Graph.HOME)


                 }
                 is Resource.Error -> {
                     Toast.makeText(mContext, loginState.message, Toast.LENGTH_SHORT).show()
                     //scaffoldState.snackbarHostState.showSnackbar(loginState.message?: "An unknown error occurred.")
                 }
                 else -> {
                     Text(
                         text = "Login",
                         fontSize = 18.sp
                     )
                 }
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