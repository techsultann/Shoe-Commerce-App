package com.panther.shoeapp.presentation.checkout

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewsScreen(
    url: String?
) {

    val viewModel: CheckoutViewModel = viewModel()
    //observe the payment state from the view model
    val paymentState = viewModel.paymentState.collectAsState()
    val paymentResponse = paymentState.value.data?.data
    Log.d("PAYMENT RESPONSE", "DATA: $url")

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .padding(16.dp)
    ) {

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            factory = { context ->
                android.webkit.WebView(context).apply {
                    webViewClient = WebViewClient()

                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                    if (url != null) {
                        loadUrl(url)
                    }
                }
            }
        )

    }
}