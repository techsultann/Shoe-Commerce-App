package com.panther.shoeapp.ui.presentation.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.panther.shoeapp.R
import com.panther.shoeapp.ui.component.DetailsBackground
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val name by viewModel.displayName.collectAsState()
    val email by viewModel.userEmail.collectAsState()

//    var selectImage by remember {
//        mutableStateOf<Uri?>(null)
//    }
//    val galleryLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        selectImage = uri
//    }


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

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.display_picture)
                    .placeholder(R.drawable.vomero_womens)
                    .build(),
                contentDescription = "profile image",
                placeholder = painterResource(id = R.drawable.vomero_womens),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(100.dp)
            )
            Log.d("Display name", "Name: $name")
            Text(
                text = name.uppercase() ,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Change Photo",
                modifier = Modifier
                    .clickable {  }
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Text(
                text = "Name",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            DetailsBackground(text = name)

            Text(
                text = "Email",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            Log.d("User Email", "Email: $email")
            DetailsBackground(text = email)

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