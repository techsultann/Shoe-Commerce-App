package com.panther.shoeapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.wallet.CreditCardExpirationDate
import com.panther.shoeapp.R
import com.panther.shoeapp.ui.theme.navyBlue

@Composable
fun PaymentCard(
    image: Int,
    cardNo: String
    ) {

    var checkedState by rememberSaveable { mutableStateOf(false ) }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "Payment card logo",
                modifier = Modifier
                    .size(75.dp)
                    .padding(start = 8.dp)
            )
            
            Spacer(modifier = Modifier.padding(8.dp))
            
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Credit Card",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = cardNo,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            RadioButton(
                selected = checkedState,
                onClick = { !checkedState },
                colors = RadioButtonDefaults.colors(
                    selectedColor = navyBlue,
                    unselectedColor = navyBlue
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewPaymentCard() {
    PaymentCard(image = R.drawable.master_card, cardNo = "1234 **** **** 5678")
}