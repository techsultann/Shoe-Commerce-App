package com.panther.shoeapp.ui.presentation.card

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TextFieldWithPlaceholder
import com.panther.shoeapp.ui.component.TopAppBar

@Composable
fun AddCardScreen() {

    var cardType by rememberSaveable { mutableStateOf("") }
    var cardHolderName by rememberSaveable { mutableStateOf("") }
    var cardNumber by rememberSaveable { mutableStateOf("") }
   // var cardType by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ADD CARD",
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

            Spacer(modifier = Modifier.padding(24.dp))

            Text(
                text = "Card Type",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            TextFieldWithPlaceholder(
                modifier = Modifier,
                value = cardType ,
                onValueChange = {
                                cardType = it
                },
                placeholder = {
                    Text(text = "Visa Card")
                }
            )

            Text(
                text = "Card Holder's Name",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            TextFieldWithPlaceholder(
                modifier = Modifier,
                value = cardHolderName ,
                onValueChange = {
                    cardHolderName = it
                },
                placeholder = {
                    Text(text = "Alexander Hussain")
                }
            )

            Text(
                text = "Card Number",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            TextFieldWithPlaceholder(
                modifier = Modifier,
                value = cardNumber ,
                onValueChange = {
                    cardNumber = it
                },
                placeholder = {
                    Text(text = "00000 0000 0000 0000")
                }
            )

            Text(
                text = "Expiry Date",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            TextFieldWithPlaceholder(
                modifier = Modifier,
                value = cardNumber ,
                onValueChange = {
                    cardNumber = it
                },
                placeholder = {
                    Text(text = "12/12")
                }
            )

            Text(
                text = "CVV",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            TextFieldWithPlaceholder(
                modifier = Modifier,
                value = cardNumber ,
                onValueChange = {
                    cardNumber = it
                },
                placeholder = {
                    Text(text = "000")
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            ShoeAppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .requiredHeight(66.dp),
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "Add Card",
                    fontSize = 18.sp
                )
            }
        }

    }
}

@Preview
@Composable
fun PreviewAddCard() {
    AddCardScreen()
}