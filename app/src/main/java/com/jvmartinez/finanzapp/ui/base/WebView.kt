package com.jvmartinez.finanzapp.ui.base

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(
    url: String,
    webViewClient: WebViewClient = WebViewClient()
) {

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                this.webViewClient = webViewClient
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
}

class CustomWebViewClient: WebViewClient(){
    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if(url != null && url.startsWith("https://example.com")){
            view?.loadUrl(url)
            return true
        }
        return false
    }
}