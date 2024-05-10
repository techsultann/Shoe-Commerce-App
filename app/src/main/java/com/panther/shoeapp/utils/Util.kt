package com.panther.shoeapp.utils

import com.panther.shoeapp.R
import com.panther.shoeapp.models.Banner

object Util {

    val Banners = listOf(
        Banner(
            id = 1,
            title = "Enjoy new Nike Products",
            image = R.drawable.motiva_womens_watkins,
            btnText = "Shop Now"
        ),
        Banner(
            id = 2,
            title = "Check out our Best selling products",
            image = R.drawable.air_zoom,
            btnText = "Shop Now"
        ),
        Banner(
            id = 3,
            title = "Subscribe to get exclusive news about us",
            image = R.drawable.ic_launcher_xx,
            btnText = "Subscribe"
        ),
        Banner(
            id = 4,
            title = "Hire me",
            image = R.drawable.techsultan,
            btnText = "Github"
        ),
    )
}