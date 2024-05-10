package com.panther.shoeapp.ui.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.panther.shoeapp.R
import com.panther.shoeapp.navigation.HomeScreenNav
import com.panther.shoeapp.ui.component.CustomDialog
import com.panther.shoeapp.ui.component.TopAppBar


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    navHostController: NavHostController
) {

    val name by viewModel.displayName.collectAsState()
    val email by viewModel.userEmail.collectAsState()
    val userData by viewModel.userData.collectAsState()
    val userDetails = userData.data
    val addressList = viewModel.getAddress.collectAsState()
    val addressData = addressList.value.data


    var numberText by rememberSaveable { mutableStateOf("") }
    var nameText by rememberSaveable { mutableStateOf("") }
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
                .verticalScroll(rememberScrollState())
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

            Text(
                text = "Hi, ${name.uppercase()}" ,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Change Photo",
                modifier = Modifier
                    .clickable {  }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Divider(
                thickness = 1.dp,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.padding(8.dp))

            val usernameDialog = remember { mutableStateOf(false) }

            ProfileItem(
                profileText = "Name",
                profileDetails = name,
                profileIcon = Icons.Outlined.Person,
                nextIcon = Icons.Outlined.Edit,
                onClick = { usernameDialog.value = true }
            )

            when {
                usernameDialog.value -> {
                    CustomDialog(
                        onDismissRequest = { usernameDialog.value = false },
                        onConfirmation = { /*TODO*/ },
                        title = "Update Username",
                        value = nameText,
                        onValueChange = {
                            nameText = it
                        }
                    )
                }
            }

            ProfileItem(
                profileText = "Email",
                profileDetails = email,
                profileIcon = Icons.Outlined.Email,
                nextIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                onClick = {  }
            )

            val userNumberDialog = remember { mutableStateOf(false) }

            if (userDetails != null) {
                ProfileItem(
                    profileText = "Number",
                    profileDetails = userDetails.phoneNumber.toString(),
                    profileIcon = Icons.Outlined.Phone,
                    nextIcon = Icons.Outlined.Edit,
                    onClick = { userNumberDialog.value = true }
                )
            }
            when {
                userNumberDialog.value -> {

                    CustomDialog(
                        onDismissRequest = { userNumberDialog.value = false },
                        onConfirmation = {
                            viewModel.updatePhoneNumber(numberText)
                            usernameDialog.value = false
                                         },
                        title = "Update phone Number",
                        value = numberText,
                        onValueChange = {
                            numberText = it
                        }
                    )
                }
            }

            if (addressData?.isNotEmpty()  == true) {

                val firstAddress = addressData.first()
                ProfileItem(
                    profileText = "Address",
                    profileDetails = "${firstAddress.state}, ${firstAddress.city}, ${firstAddress.street}, ${firstAddress.postcode}",
                    profileIcon = Icons.Outlined.LocationOn,
                    nextIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    onClick = { navHostController.navigate(HomeScreenNav.AddressListScreen.route) }
                )
            } else {
                ProfileItem(
                    profileText = "Address",
                    profileDetails = "No address available",
                    profileIcon = Icons.Outlined.LocationOn,
                    nextIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    onClick = { navHostController.navigate(HomeScreenNav.AddressListScreen.route) }
                )
            }

            ProfileItemWithoutDetails(
                profileText = "Forgot Password",
                profileIcon = Icons.Outlined.Lock ,
                nextIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                onClick = {  },
                modifier = Modifier
            )

            ProfileItemWithoutDetails(
                profileText = "Logout",
                profileIcon = Icons.AutoMirrored.Outlined.Logout,
                nextIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                onClick = {  },
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(16.dp))

            Divider(
                thickness = 3.dp,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileItemWithoutDetails(
                profileText = "Delete Account",
                profileIcon = Icons.Outlined.WarningAmber ,
                nextIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                onClick = {  },
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(100.dp))

        }

    }
}


@Composable
fun ProfileItem(
    profileText: String,
    profileDetails: String,
    profileIcon: ImageVector,
    nextIcon: ImageVector,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = profileIcon, contentDescription = "Location Icon")

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = profileText,
                fontSize = 18.sp
            )
            Text(
                text = profileDetails,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        Icon(
            imageVector = nextIcon,
            contentDescription = "Forward icon",
            modifier = Modifier
                .clickable { onClick() }
        )
    }
}

@Composable
fun ProfileItemWithoutDetails(
    profileText: String,
    profileIcon: ImageVector,
    nextIcon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier
) {

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = profileIcon, contentDescription = "Location Icon")

        Text(
            text = profileText,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
        )

        Icon(
            imageVector = nextIcon,
            contentDescription = "Forward icon",
            modifier = Modifier
                .clickable { onClick() }
        )
    }
}