package com.panther.shoeapp.ui.presentation.card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panther.shoeapp.R
import com.panther.shoeapp.ui.theme.CardColor
import com.panther.shoeapp.ui.theme.CardLightColor

@Composable
fun AddCreditCard(
    cardType: String?,
    name: String?,
    cardNumber: String?
) {
    var rotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500), label = ""
    )

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(230.dp)
            .clickable {
                rotated = !rotated
            }
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardColor
        )
    ){
        if (!rotated) {
            Box(
                modifier = Modifier
            ) {


                Canvas(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxSize()
                        .clipToBounds()
                        .graphicsLayer {
                            alpha = animateFront
                        }
                ) {
                    drawArc(
                        color = CardLightColor,
                        -180f,
                        180f,
                        useCenter = false,
                        size = Size(size.width, (size.height * 1.99).toFloat()),
                        //size = Size(size.width, size.height * 2),
                        // style = Stroke(8f.toDp().toPx(), cap = StrokeCap.Round)
                    )
                }

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(300.dp)
                        .clipToBounds()
                        .absoluteOffset(50.dp, (-15).dp)
                        .rotate(200f)
                        .padding(top = 100.dp)
                        .graphicsLayer {
                            alpha = animateFront
                        }
                ) {
                    drawArc(
                        color = Color.White.copy(alpha = 0.1f),
                        180f,
                        180f,
                        useCenter = false,
                        size = Size(size.width, size.height),
                        style = Stroke(4f.toDp().toPx(), cap = StrokeCap.Round)
                    )
                }


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (cardType != null) {
                            Text(
                                text = cardType,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 2.sp,
                                modifier = Modifier
                                    .graphicsLayer {
                                        alpha = animateFront
                                    }
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.card_icon),
                            contentDescription = "card icon",
                            modifier = Modifier
                                .padding(16.dp)
                                .size(20.dp)
                                .graphicsLayer {
                                    alpha = animateFront
                                }
                        )

                    }

                    Image(
                        painter = painterResource(id = R.drawable.card_chip),
                        contentDescription = "chip icon",
                        modifier = Modifier
                            .padding(start = 16.dp, bottom = 8.dp)
                            .size(50.dp)
                            .graphicsLayer {
                                alpha = animateFront
                            }
                    )

                    if (cardNumber != null) {
                        Text(
                            text = cardNumber,
                            fontSize = 24.sp,
                            color = Color.White,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .graphicsLayer {
                                    alpha = animateFront
                                }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        if (name != null) {
                            Text(
                                text = name,
                                fontSize = 14.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .graphicsLayer {
                                        alpha = animateFront
                                    }
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.master_card),
                            contentDescription = "card type icon",
                            modifier = Modifier
                                .size(50.dp)
                                .graphicsLayer {
                                    alpha = animateFront
                                }
                        )
                    }


                }

            }

        } else {
            Divider(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .graphicsLayer {
                        alpha = animateBack
                    },
                color = Color.Black,
                thickness = 50.dp
            )

            Text(
                text = "123",
                color = Color.Black,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .background(Color.White)
                    .graphicsLayer {
                        alpha = animateBack
                        rotationY = rotation
                    }
                    .padding(10.dp),

                fontSize = 15.sp,
                textAlign = TextAlign.End
            )

            Text(
                text = " Tech Sultan",
                color = Color.White.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = animateBack
                        rotationY = rotation
                    }
                    .padding(5.dp),
                fontWeight = FontWeight.Thin,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

