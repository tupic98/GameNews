package com.alvarenga.gamenews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.RelativeLayout

class LoginActivity : AppCompatActivity() {

    lateinit var relative1:RelativeLayout
    var handler = Handler()
    var runnable:Runnable = object:Runnable {
        override fun run() {
            relative1.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        relative1 = findViewById(R.id.RelLogoB)

        handler.postDelayed(runnable, 2000)
    }
}
