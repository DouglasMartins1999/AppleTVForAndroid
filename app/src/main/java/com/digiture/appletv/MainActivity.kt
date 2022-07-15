package com.digiture.appletv

import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.webkit.WebChromeClient.CustomViewCallback
import android.widget.FrameLayout
import android.webkit.WebChromeClient
import android.content.pm.ActivityInfo

class MainActivity : AppCompatActivity() {
    private var myWebView: WebView? = null
    private var myCustomView: View? = null;
    private var myCustomViewContainer: FrameLayout? = null;
    private var customViewCallback: CustomViewCallback? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myWebView = findViewById(R.id.webview)
        myCustomViewContainer = findViewById<FrameLayout>(R.id.customViewContainer)

        myWebView?.settings?.javaScriptEnabled = true
        myWebView?.settings?.databaseEnabled = true
        myWebView?.settings?.domStorageEnabled = true
        myWebView?.settings?.useWideViewPort = true
        myWebView?.settings?.javaScriptCanOpenWindowsAutomatically = true
        myWebView?.settings?.mediaPlaybackRequiresUserGesture = false
        myWebView?.settings?.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        myWebView?.settings?.userAgentString = "Mozilla/5.0 (Linux; Android 8.0.0; SM-G955U Build/R16NW) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Mobile Safari/537.36"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            myWebView?.settings?.forceDark = WebSettings.FORCE_DARK_ON
        }

        myWebView?.webViewClient = WebViewClient()
        myWebView?.webChromeClient = object: WebChromeClient() {
            private var originalOrientation: Int = resources.configuration.orientation;
            private var originalSystemUIVisibility: Int = window.decorView.systemUiVisibility;

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

            override fun onShowCustomView(view: View, requestedOrientation: Int, callback: CustomViewCallback) {
                onShowCustomView(view, callback)
            }

            override fun onShowCustomView(view: View, callback: CustomViewCallback) {
                if (myCustomView != null) {
                    callback.onCustomViewHidden()
                    return
                }

                myCustomView = view
                myWebView?.visibility = View.GONE

                myCustomViewContainer?.visibility = View.VISIBLE
                myCustomViewContainer?.addView(view)
                customViewCallback = callback

                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                originalSystemUIVisibility = window.decorView.systemUiVisibility;
                window.decorView.systemUiVisibility = 3846
            }

            override fun onHideCustomView() {
                super.onHideCustomView()

                if (myCustomView == null)
                    return

                myWebView?.visibility = View.VISIBLE
                myCustomViewContainer?.visibility = View.GONE
                myCustomView?.visibility = View.GONE

                window.decorView.systemUiVisibility = originalSystemUIVisibility
                requestedOrientation = originalOrientation;

                myCustomViewContainer?.removeView(myCustomView)
                customViewCallback!!.onCustomViewHidden()
                myCustomView = null
            }
        }

        if (savedInstanceState == null) {
            myWebView?.loadUrl("https://tv.apple.com/br/")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.window.statusBarColor = ContextCompat.getColor(this, R.color.colorStatusBar)
            this.window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState!!)
        myWebView?.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState!!)
        myWebView?.restoreState(savedInstanceState)
    }

    override fun onBackPressed() {
        if (myCustomView != null) {
            myWebView?.webChromeClient?.onHideCustomView();
        } else if (myWebView?.canGoBack() == true) {
            myWebView?.goBack()
        } else {
            super.onBackPressed()
        }
    }
}