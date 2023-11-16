package com.panther.shoeapp.ui.presentation.auth.forgot_password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panther.shoeapp.ui.component.PasswordTextField
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.theme.navyBlue

@Composable
fun ForgotPasswordPass() {

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

        PasswordTextField()
        
        Spacer(modifier = Modifier.padding(16.dp))
        
        Text(text = "Confirm New Password", color = navyBlue)

        PasswordTextField()

        Spacer(modifier = Modifier.padding(16.dp))

        ShoeAppButton(
            onClick = { /*TODO*/ },
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

    ForgotPasswordPass()
}