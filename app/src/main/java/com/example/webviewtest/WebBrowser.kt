package com.example.webviewtest

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.web.*

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebBrowser() {
    val url by remember { mutableStateOf("https://vr.qurancomplex.gov.sa/msq/") }
    val state = rememberWebViewState(url = url)
    val navigator = rememberWebViewNavigator()

    Column {

        TopAppBar(title = { Text(text = "Control") },
            navigationIcon = {
                IconButton(onClick = { navigator.navigateBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
//                IconButton(onClick = { navigator.navigateForward() }) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowForward,
//                        contentDescription = "Forward"
//                    )
//                }
//
//                IconButton(onClick = { navigator.reload() }) {
//                    Icon(
//                        imageVector = Icons.Default.Refresh,
//                        contentDescription = "Refresh"
//                    )
//                }
            }
        )


        val loadingState = state.loadingState
        if (loadingState is LoadingState.Loading) {
            LinearProgressIndicator(
                progress = loadingState.progress,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // A custom WebViewClient and WebChromeClient can be provided via subclassing
        val webClient = remember {
            object : AccompanistWebViewClient() {
                override fun onPageStarted(
                    view: WebView,
                    url: String?,
                    favicon: Bitmap?
                ) {
                    super.onPageStarted(view, url, favicon)
                    Log.d("Accompanist WebView", "Page started loading for $url")
                }
            }
        }

        WebView(
            state = state,
            modifier = Modifier.weight(1f),
            navigator = navigator,
            onCreated = { webView ->
                webView.settings.javaScriptEnabled = true
            },
            client = webClient
        )
    }
}