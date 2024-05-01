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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.panther.shoeapp.navigation.HomeScreenNav
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TextFieldWithPlaceholder
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    navHostController: NavHostController,
    viewModel : CreditCardVieModel = viewModel()
) {
    val creditCardState by viewModel.uploadCardDetails.collectAsState()
    var isCardTypeExpanded by rememberSaveable { mutableStateOf(false) }

    var cardType by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var cardNumber by rememberSaveable { mutableStateOf("") }
    var expiryDate by rememberSaveable { mutableStateOf("") }
    var cvv by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = creditCardState) {
        when (creditCardState) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                navHostController.popBackStack()
                navHostController.navigate("${HomeScreenNav.PaymentCardScreen.route}/$cardType/$name/$cardNumber/$cvv")
            }
            is Resource.Error -> {

            }
        }
    }

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

            ExposedDropdownMenuBox(
                expanded = isCardTypeExpanded,
                onExpandedChange = { newValue ->
                    isCardTypeExpanded = newValue
                }
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(CircleShape)
                        .clipToBounds()
                        .requiredHeight(66.dp)
                        .menuAnchor(),
                    value = cardType,
                    onValueChange = {},
                    placeholder = { Text(text = "Card Type") },
                    shape = CircleShape,
                    readOnly = true,
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCardTypeExpanded)
                    }
                )

                ExposedDropdownMenu(expanded = isCardTypeExpanded, onDismissRequest = { isCardTypeExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Master Card") },
                        onClick = {
                            cardType = "Master Card"
                            isCardTypeExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                    DropdownMenuItem(
                        text = { Text(text = "VISA") },
                        onClick = {
                            cardType = "VISA"
                            isCardTypeExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                    DropdownMenuItem(
                        text = { Text(text = "VERVE") },
                        onClick = {
                            cardType = "VERVE"
                            isCardTypeExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )

                }
            }

            Text(
                text = "Card Holder's Name",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            TextFieldWithPlaceholder(
                modifier = Modifier,
                value = name ,
                onValueChange = {
                    name = it
                },
                placeholder = {
                    Text(text = "Alexander Hussain")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions()
            )

            Text(
                text = "Card Number",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            TextField(
                value = cardNumber,
                onValueChange = { cardNumber = it.take(16) },
                placeholder = {
                    Text(text = "00000 0000 0000 0000")
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(CircleShape)
                    .clipToBounds()
                    .requiredHeight(66.dp),
                shape = CircleShape,
                visualTransformation = { text ->
                    val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
                    var out = ""

                    for (i in trimmed.indices) {
                        out += trimmed[i]
                        if (i % 4 == 3 && i != 15) out += "-"
                    }
                    TransformedText(
                        AnnotatedString(out), 
                        creditCardOffsetMapping
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
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
                value = expiryDate ,
                onValueChange = {
                    expiryDate = it
                },
                placeholder = {
                    Text(text = "12/12")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions()
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
                value = cvv ,
                onValueChange = {
                    cvv = it
                },
                placeholder = {
                    Text(text = "000")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions()
            )

            Spacer(modifier = Modifier.weight(1f))

            ShoeAppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .requiredHeight(66.dp),
                onClick = {
                    viewModel.saveCardDetails(
                        cardType,
                        name,
                        cardNumber
                    )
                    navHostController.navigate(route = "${HomeScreenNav.PaymentCardScreen.route}/$cardType/$name/$cardNumber")
                }
            ) {
                Text(
                    text = "Add Card",
                    fontSize = 18.sp
                )
            }
        }

    }
}

// Making XXXX-XXXX-XXXX-XXXX string.
val creditCardOffsetMapping = object : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        if (offset <= 3) return offset
        if (offset <= 7) return offset + 1
        if (offset <= 11) return offset + 2
        if (offset <= 16) return offset + 3
        return 19
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (offset <= 4) return offset
        if (offset <= 9) return offset - 1
        if (offset <= 14) return offset - 2
        if (offset <= 19) return offset - 3
        return 16
    }
}

//@Preview
//@Composable
//fun PreviewAddCard() {
//    AddCardScreen()
//}