package com.github.ogapants.webviewwithviewgroup

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.*

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: ArticleWebView
    private lateinit var container: ArticleContainer

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webView = findViewById(R.id.webView)
        container = findViewById(R.id.articleLayout)

        webView.setOnScrollChangeListenerCompat(object :ArticleWebView.OnScrollChangeListenerCompat{
            override fun onScrollChange(v: View, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                container.dispatchScroll(oldScrollY, scrollY)
            }
        })
        WebView.setWebContentsDebuggingEnabled(true)
        webView.settings.apply {
            javaScriptEnabled = true
            useWideViewPort = false
            builtInZoomControls = false
            loadWithOverviewMode = false
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            setAppCacheEnabled(true)
            setSupportZoom(false)
        }
        webView.webChromeClient = object :WebChromeClient(){
            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                Log.d("WebViewActivity", "onConsoleMessage ${consoleMessage?.message()}")
                return super.onConsoleMessage(consoleMessage)
            }
        }
        webView.addJavascriptInterface(JavascriptBridge(), "JS")

        val htmlText: String = String.format(
            HtmlTemplate.template,
            webView.screenPxToWebPx(container.fetchHeaderHeight()),
            HtmlTemplate.body,
            webView.screenPxToWebPx(container.fetchFooterHeight())
        )
        webView.loadData(htmlText, "text/html", "UTF-8")
    }

    private fun onDomContentLoaded(contentHeight: Int) {
        val webContentHeightDp = webView.webPxToScreenPx(contentHeight)
        container.onReady(webContentHeightDp)
    }

    private inner class JavascriptBridge {

        @Suppress("unused", "FunctionName")
        @JavascriptInterface
        fun _onDomContentLoaded(contentHeight: Int) {
            Log.d("WebViewActivity", "_onDomContentLoaded")
//            Handler(Looper.getMainLooper()).post {
//                onDomContentLoaded(contentHeight)
//            }
        }

        @Suppress("unused", "FunctionName")
        @JavascriptInterface
        fun _onContentReady(contentHeight: Int){
            Handler(Looper.getMainLooper()).post {
                Log.d("WebViewActivity", "_onContentReady")
                onDomContentLoaded(contentHeight)
            }
        }
    }
}
