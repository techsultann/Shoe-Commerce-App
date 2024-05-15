package com.panther.shoeapp.presentation.address

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.panther.shoeapp.navigation.HomeScreenNav
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.theme.navyBlue

@Composable
fun AddressList(
    navHostController: NavHostController
) {

    val viewModel: AddressViewModel = viewModel()
    val addressList = viewModel.getAddress.collectAsState()

    LaunchedEffect(addressList) {

        viewModel.getAddressList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ADDRESS",
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
                                navHostController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
    ){ paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {

                val address = addressList.value.data ?: emptyList()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                ) {

                    items(address) { addressData ->

                        AddressItem(
                            name = "${addressData.lastName} ${addressData.firstName}",
                            state = addressData.state,
                            street = addressData.street!!,
                            number = addressData.phoneNumber!!,
                            onClick = {
                                navHostController.navigate("${HomeScreenNav.EditAddressScreen.route}/${addressData.id}")
                            }
                        )
                    }
                }


            ShoeAppButton(
                onClick = {
                    navHostController.popBackStack()
                    navHostController.navigate(HomeScreenNav.AddAddressScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Add Address")
            }

        }
    }


}

@Composable
fun AddressItem(
    name: String?,
    state: String?,
    street: String?,
    number: String?,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .height(120.dp)
    ) {

        val width = LocalConfiguration.current.screenWidthDp.dp / 8f
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "location icon"
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = name.toString(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = state.toString(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = street.toString(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                if (number != null) {
                    Text(
                        text = number,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }

            Text(
                text = "Edit",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { onClick() }
            )
        }

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

            Text(text = "Set this as default address", Modifier.padding(start = 8.dp, top = 16.dp))
        }


    }



}

@Preview(backgroundColor = 0xffFFFFFF)
@Composable
fun PreviewAddressItem() {
    AddressItem(name = "sultan", state = "lagos", street = "shyllon", number = "09056786533") {

    }
}