package com.digiture.appletv

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private var myWebView: WebView? = null
    private var webSettings: WebSettings? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myWebView = findViewById(R.id.webview)
        webSettings = myWebView?.settings

        webSettings?.javaScriptEnabled = true
        webSettings?.databaseEnabled = true
        webSettings?.domStorageEnabled = true
        webSettings?.javaScriptCanOpenWindowsAutomatically = true
        webSettings?.mediaPlaybackRequiresUserGesture = false
        webSettings?.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webSettings?.userAgentString = "Mozilla/5.0 (Linux; Android 10; SM-G9600) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.50 Mobile Safari/537.36"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            webSettings?.forceDark = WebSettings.FORCE_DARK_ON
        }

        myWebView?.webViewClient = WebViewClient()
        myWebView?.loadUrl("https://tv.apple.com/br/")
        myWebView?.webChromeClient = object: WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                val resources = request.resources

                for (i in resources.indices) {
                    if (PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID == resources[i]) {
                        request.grant(resources)
                        return
                    }
                }

                super.onPermissionRequest(request)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.window.statusBarColor = ContextCompat.getColor(this, R.color.colorStatusBar)
            this.window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
    }

    override fun onBackPressed() {
        if (myWebView?.canGoBack() == true) {
            myWebView?.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
