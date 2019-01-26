package com.github.ogapants.webviewwithviewgroup

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView

class WebViewActivity : AppCompatActivity() {

    lateinit var webView: ArticleWebView
    lateinit var container: ArticleContainer

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
        container.bind(webView)


        WebView.setWebContentsDebuggingEnabled(true)
        val settings = webView.settings
        settings.setAppCacheEnabled(true)
        settings.setJavaScriptEnabled(true)
        settings.setUseWideViewPort(false)
        settings.setSupportZoom(false)
        settings.setBuiltInZoomControls(false)
        settings.setLoadWithOverviewMode(false)
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS)
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
        container.onPageLoaded(webContentHeightDp)
    }

    private inner class JavascriptBridge {

        @Suppress("unused")
        @JavascriptInterface
        fun _onDomContentLoaded(contentHeight: Int) {
            Handler(Looper.getMainLooper()).post {
                onDomContentLoaded(contentHeight)
            }
        }
    }
}
