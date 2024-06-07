package com.panther.shoeapp.presentation.checkout

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.panther.shoeapp.navigation.HomeScreenNav

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewsScreen(
    url: String?,
    onBackPressed: () -> Unit,
    navHostController: NavHostController
) {

    val viewModel: CheckoutViewModel = viewModel()
    //observe the payment state from the view model
    val paymentState = viewModel.paymentState.collectAsState()
    val paymentResponse = paymentState.value.data?.data
    var mWebView: WebView? = null
    var backEnabled by rememberSaveable { mutableStateOf(false) }

    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "",
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFF152354)
//
//                    )
//                },
//                navigationIcon = {
//                    Surface(
//                        modifier = Modifier
//                            .size(height = 56.dp, width = 56.dp)
//                            .clip(shape = CircleShape),
//                        tonalElevation = 4.dp,
//                        shadowElevation = 4.dp
//                    ) {
//                        IconButton(
//                            onClick = {
//                                mWebView?.invalidate()
//                                mWebView?.clearFocus()
//                                mWebView?.destroy()
//                                mWebView = null
//                                navHostController.popBackStack()
//
//                            }
//                        ) {
//                            Icon(
//                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                                contentDescription = "Navigation back icon",
//                                tint = Color.Unspecified
//                            )
//                        }
//
//                    }
//                },
//                actions = {
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(
//                            imageVector = Icons.Outlined.Notifications,
//                            contentDescription = "light theme icon",
//                            tint = Color.Unspecified
//                        )
//
//                    }
//
//                }
//            )
//        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                factory = { context ->

                    WebView(context).apply {

                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        webViewClient = object : WebViewClient() {

                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                if (url != null && url.startsWith("https://techsultan.com")) {
                                    navHostController.navigate("${HomeScreenNav.SuccessfulScreen.route}/{status}/{tx_ref}/{transaction_id}")
                                    return true
                                }

                                return false
                            }
                        }

                        settings.javaScriptEnabled = true
                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true
                        settings.setSupportZoom(true)
                        mWebView = this

                        if (url != null) {
                            loadUrl(url)
                        }
                    }

                }, update = {
                    if (url != null) {
                        mWebView?.loadUrl(url)
                    }
                }

            )

            BackHandler(enabled = backEnabled) {
                if (mWebView!!.canGoBack()) {
                    mWebView?.goBack()
                } else {
                    onBackPressed()
                }
            }

        }
    }

}