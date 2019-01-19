package com.github.ogapants.webviewwithviewgroup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView

class ScrollViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_view)

        val webView = findViewById<WebView>(R.id.webView_scrollview)
        val htmlText: String = HtmlTemplate.simpleHtml
        webView.loadData(htmlText,"text/html", "UTF-8")
    }
}
