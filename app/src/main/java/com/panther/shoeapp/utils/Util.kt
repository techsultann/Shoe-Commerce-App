package com.panther.shoeapp.utils

import com.panther.shoeapp.R
import com.panther.shoeapp.models.Banner

object Util {

    val Banners = listOf(
        Banner(
            id = 1,
            image = R.drawable.second_banner,
            btnText = "Shop Now"
        ),
        Banner(
            id = 2,
            image = R.drawable.first_banner,
            btnText = "Subscribe"
        ),
        Banner(
            id = 3,
            image = R.drawable.third_banner,
            btnText = "Github"
        ),
    )
}