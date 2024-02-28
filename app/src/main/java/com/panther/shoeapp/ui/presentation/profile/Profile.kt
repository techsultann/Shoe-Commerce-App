package com.panther.shoeapp.ui.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panther.shoeapp.R
import com.panther.shoeapp.ui.component.DetailsBackground
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar

@Composable
fun ProfileScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "PROFILE",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF152354)

                    )
                },
                navigationIcon = {
                    Surface(
                        modifier = Modifier
                            .size(height = 56.dp, width = 56.dp)
                            .clip(shape = CircleShape),
                        tonalElevation = 4.dp,
                        shadowElevation = 4.dp
                    ) {
                        IconButton(
                            onClick = {

                            }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Navigation back icon",
                                tint = Color.Unspecified
                            )
                        }

                    }
                },
                actions = {

                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painterResource(id = R.drawable.vomero_womens),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            
            Text(
                text = "Alexander Hussain",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Change Photo")

            Spacer(modifier = Modifier.padding(16.dp))

            Text(
                text = "Name",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            DetailsBackground(text = "Alexander Hussain")

            Text(
                text = "Email",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            DetailsBackground(text = "Sadamhussain@gmail.com")

            Text(
                text = "Number",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            DetailsBackground(text = "08133427487")

            Text(
                text = "Address",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            DetailsBackground(text = "Toodely Benson Allentown, New Mexico 31134.")

            Spacer(modifier = Modifier.weight(1f))

            ShoeAppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .requiredHeight(66.dp),
                onClick = { /*TODO*/ },
                enabled = false
            ) {
                Text(
                    text = "Save Changes",
                    fontSize = 18.sp
                )
            }
        }

    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}