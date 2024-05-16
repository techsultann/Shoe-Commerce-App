package com.panther.shoeapp.presentation.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.panther.shoeapp.R
import com.panther.shoeapp.navigation.BottomBarScreen
import com.panther.shoeapp.navigation.HomeScreenNav
import com.panther.shoeapp.ui.component.CardButton
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.theme.FieldColor

@Composable
fun CardScreen(
    cardType: String?,
    name: String?,
    cardNumber:String?,
    cvv: String?,
    navHostController: NavHostController
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CARDS",
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

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
               // CardButton(boolean = true, icon = R.drawable.apple_pay, onClick = {})
                CardButton(boolean = true, icon = R.drawable.visa_logo, onClick = {})
                CardButton(boolean = true, icon = R.drawable.master_card, onClick = {})
                CardButton(
                    boolean = true,
                    icon = R.drawable.ic_round_add,
                    onClick = {
                        navHostController.navigate(route = HomeScreenNav.AddCardScreen.route)
                    }
                )
            }

            CreditCard(cardType, name, cardNumber, cvv)

            Spacer(modifier = Modifier.padding(16.dp))

            Text(
                text = "Card Number",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            CardTextBg(text = cardNumber!!)

            Text(
                text = "Card Holder Name",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            CardTextBg(text = name!!)

            Spacer(modifier = Modifier.weight(1f))

            ShoeAppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .requiredHeight(66.dp),
                onClick = {
                    navHostController.popBackStack()
                    navHostController.navigate(route = BottomBarScreen.Home.route)
                }
            ) {
                Text(
                    text = "Done",
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun CardTextBg(
    text: String
) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .height(65.dp)
            .fillMaxWidth(),
        color = FieldColor,
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(FieldColor)
                    .clip(CircleShape)
            )
        }

    }
}
