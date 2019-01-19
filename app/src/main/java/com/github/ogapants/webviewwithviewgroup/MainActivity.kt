package com.github.ogapants.webviewwithviewgroup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.webViewButton).setOnClickListener {
            //startActivity(Intent(this@MainActivity, ScrollViewActivity::class.java))
        }
        findViewById<Button>(R.id.scrollViewButton).setOnClickListener {
            startActivity(Intent(this@MainActivity, ScrollViewActivity::class.java))
        }
    }
}
