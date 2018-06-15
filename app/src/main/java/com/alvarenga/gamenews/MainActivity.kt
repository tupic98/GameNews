package com.alvarenga.gamenews

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.alvarenga.gamenews.db.AppDatabase
import com.alvarenga.gamenews.db.AppDatabase_Impl
import com.alvarenga.gamenews.db.viewmodels.NewsViewModel
import com.alvarenga.gamenews.extras.Utils.Companion.CheckNetConnection
import com.alvarenga.gamenews.fragments.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.share
import org.apache.commons.io.FileUtils

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var appDB: AppDatabase
    lateinit var newsVM:NewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsVM = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        if(CheckLogin()){
            setContentView(R.layout.activity_main)
            if(!CheckNetConnection(this))
                Toast.makeText(this, "No network connection", Toast.LENGTH_SHORT).show()
            appDB = AppDatabase.getInstanceDatabase(applicationContext)!!
            setContentView(R.layout.activity_main)
            setSupportActionBar(toolbar)
            val toggle = ActionBarDrawerToggle(
                    this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            drawer_layout.addDrawerListener(toggle)
            toggle.syncState()
            val fragment:Fragment = NewsFragment()
            supportFragmentManager.beginTransaction().replace(R.id.FrameLayout,fragment).commit()
            supportActionBar!!.title = title
            nav_view.setNavigationItemSelectedListener(this)
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragmentTransaction = false
        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.nav_camera -> {
                fragment = NewsFragment()
                fragmentTransaction = true
            }
            R.id.nav_gallery -> {
                fragment = NewsFragment()
                fragmentTransaction = true
            }
            R.id.nav_slideshow -> {
                fragment = NewsFragment()
                fragmentTransaction = true
            }
            R.id.nav_manage -> {
                fragment = NewsFragment()
                fragmentTransaction = true
            }
            R.id.nav_settings -> {
                fragment = NewsFragment()
                fragmentTransaction = true
            }
            R.id.nav_logout -> {
                SignOut()
                fragment = null
                fragmentTransaction = false
            }

        }
        if(fragmentTransaction){
            supportFragmentManager.beginTransaction().replace(R.id.FrameLayout,fragment).commit()
            supportActionBar!!.title = item.title
            item.isChecked = true
        }else{
            item.isChecked = false
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun CheckLogin():Boolean{
        val sharedPref:SharedPreferences = this.getSharedPreferences("Login", Context.MODE_PRIVATE)
        return sharedPref.contains("token")
    }


    private fun SignOut(){
        val sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()){
            clear()
            apply()
        }
        newsVM.deleteAllNews()
        startActivity(Intent(this, LoginActivity::class.java))
        FileUtils.deleteQuietly(cacheDir)
        finish()
    }
}
