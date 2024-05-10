package com.panther.shoeapp.ui.presentation.address

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.navigation.HomeScreenNav
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.utils.Resource

@Composable
fun EditAddress(
    navHostController: NavHostController,
    addressId: String?,
    viewModel: AddressViewModel = viewModel()
) {
    val editAddressState by viewModel.addressById.collectAsState()
    Log.d("ADDRESS 1", "Address: ${editAddressState.data}")

        if (addressId != null) {
            viewModel.getAddressById(addressId)
        }

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "EDIT ADDRESS",
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
    ) { paddingValues ->

        when (editAddressState) {

            is Resource.Loading -> {

                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                val addressDetails = editAddressState.data
                Log.d("ADDRESS", "Address: $addressDetails")
                var firstName by rememberSaveable { mutableStateOf(addressDetails?.firstName ?: "" ) }
                var lastName by rememberSaveable { mutableStateOf(addressDetails?.lastName ?: "" ) }
                var street by rememberSaveable { mutableStateOf(addressDetails?.street ?: "" ) }
                var apartmentBuilding by rememberSaveable { mutableStateOf(addressDetails?.apartment ?: "" ) }
                var city by rememberSaveable { mutableStateOf(addressDetails?.city ?: "" ) }
                var postcode by rememberSaveable { mutableStateOf(addressDetails?.postcode ?: "" ) }
                var phoneNumber by rememberSaveable { mutableStateOf( addressDetails?.phoneNumber ?: "" ) }
                var state by rememberSaveable { mutableStateOf(addressDetails?.state ?: "" ) }
                var buttonClicked by rememberSaveable { mutableStateOf(false) }

                val validateForm = firstName.isNotEmpty() && lastName.isNotEmpty() && street.isNotEmpty()
                        && postcode.isNotEmpty() && phoneNumber.isNotEmpty() && state.isNotEmpty()

                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(horizontal = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(100.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val width = LocalConfiguration.current.screenWidthDp.dp / 8f
                        OutlinedTextField(
                            value = firstName,
                            onValueChange = {
                                firstName = it
                            },
                            placeholder = {
                                Text(text = "First name")
                            },
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .weight(1f)
                                .width(width),
                            supportingText = {
                                if (buttonClicked && firstName.isEmpty()) Text(text = "This field cannot be empty")
                            },
                            isError = buttonClicked && firstName.isEmpty(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Text
                            )
                        )

                        OutlinedTextField(
                            value = lastName,
                            onValueChange = {
                                lastName = it
                            },
                            placeholder = {
                                Text(text = "Last name")
                            },
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .weight(1f)
                                .width(width),
                            supportingText = {
                                if (buttonClicked && lastName.isEmpty()) Text(text = "This field cannot be empty")
                            },
                            isError = buttonClicked && lastName.isEmpty(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Text
                            )
                        )
                    }



                    OutlinedTextField(
                        value = street,
                        onValueChange = {
                            street = it
                        },
                        placeholder = {
                            Text(text = "Street")
                        },
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = apartmentBuilding,
                        onValueChange = {
                            apartmentBuilding = it
                        },
                        placeholder = {
                            Text(text = "Apartment, building ...")
                        },
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        supportingText = {
                            if (buttonClicked && street.isEmpty()) Text(text = "This field cannot be empty")
                        },
                        isError = buttonClicked && lastName.isEmpty(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        val width = LocalConfiguration.current.screenWidthDp.dp / 8f
                        OutlinedTextField(
                            value = city,
                            onValueChange = {
                                city = it
                            },
                            placeholder = {
                                Text(text = "City")
                            },
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .width(width)
                                .weight(1f),
                            supportingText = {
                                if (buttonClicked && city.isEmpty()) Text(text = "This field cannot be empty")
                            },
                            isError = buttonClicked && city.isEmpty(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Text
                            )
                        )

                        OutlinedTextField(
                            value = postcode,
                            onValueChange = {
                                postcode = it
                            },
                            placeholder = {
                                Text(text = "Postcode")
                            },
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .width(width)
                                .weight(1f),
                            supportingText = {
                                if (buttonClicked && postcode.isEmpty()) Text(text = "This field cannot be empty")
                            },
                            isError = buttonClicked && postcode.isEmpty(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Number
                            )
                        )
                    }

                    OutlinedTextField(
                        value = state,
                        onValueChange = {
                            state = it
                        },
                        placeholder = {
                            Text(text = "State")
                        },
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        supportingText = {
                            if (buttonClicked && state.isEmpty()) Text(text = "This field cannot be empty")
                        },
                        isError = buttonClicked && state.isEmpty(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = {
                            phoneNumber = it
                        },
                        placeholder = {
                            Text(text = "Phone Number")
                        },
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        supportingText = {
                            if (buttonClicked && phoneNumber.isEmpty()) Text(text = "This field cannot be empty")
                        },
                        isError = buttonClicked && phoneNumber.isEmpty(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Phone
                        )
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    ShoeAppButton(
                        onClick = {
                            buttonClicked = true
                            if (validateForm) {

                                viewModel.updateAddress(
                                    addressId!!,
                                    firstName,
                                    lastName,
                                    street,
                                    apartmentBuilding,
                                    city,
                                    postcode,
                                    state,
                                    phoneNumber
                                )
                                navHostController.popBackStack()
                                navHostController.navigate(HomeScreenNav.AddressListScreen.route)
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Text(text ="Update Address")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = {
                            viewModel.deleteAddress(addressId!!)
                            navHostController.popBackStack()
                            navHostController.navigate(HomeScreenNav.AddressListScreen.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(

                        ),
                        contentPadding = ButtonDefaults.ContentPadding
                    ) {
                        Text(text ="Delete Address")
                    }

                    Spacer(modifier = Modifier.height(100.dp))
                }

            }
            is Resource.Error -> {

            }
        }
    }




}

@Preview
@Composable
fun PreviewAddAddress() {
    AddAddress(rememberNavController())
}