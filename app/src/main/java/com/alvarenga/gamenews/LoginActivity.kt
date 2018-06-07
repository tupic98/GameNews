package com.alvarenga.gamenews


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import com.alvarenga.gamenews.gamenewsuca.api.APIRequest

class LoginActivity : AppCompatActivity() {

    lateinit var usernametext:String
    lateinit var passwordtext:String
    lateinit var usernamefield:EditText
    lateinit var passwordfield:EditText
    lateinit var loginbutton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        settingViews()

        loginbutton.setOnClickListener{LoginButtonOperation(it)}

        if(savedInstanceState != null){
            usernamefield.apply {
                (savedInstanceState.getString("username")) }
            passwordfield.apply {
                savedInstanceState.getString("password")
            }
        }
    }

    override fun onSaveInstanceState(outState:Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("username",usernametext)
        outState.putString("password",passwordtext)
    }

    private fun LoginButtonOperation(button:View){
        var fragmentManager: FragmentManager = supportFragmentManager
        APIRequest.login(usernamefield.text.toString(),passwordfield.text.toString(),this,fragmentManager)
    }

    private fun settingViews() {
        usernamefield = findViewById(R.id.usernameedit)
        usernametext = usernamefield.text.toString()
        passwordfield = findViewById(R.id.passwordedit)
        passwordtext = passwordfield.text.toString()
        loginbutton = findViewById(R.id.loginbutton)
        loginbutton.setOnClickListener{ LoginButtonOperation(it)}
    }
}
