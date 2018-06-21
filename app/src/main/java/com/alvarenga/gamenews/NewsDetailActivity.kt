package com.alvarenga.gamenews

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class NewsDetailActivity:AppCompatActivity(){
    lateinit var title:String
    lateinit var description:String
    lateinit var content:String
    lateinit var image:String
    lateinit var news_title:TextView
    lateinit var news_desc:TextView
    lateinit var news_content:TextView
    lateinit var news_image: ImageView
    lateinit var collapsingToolbar:CollapsingToolbarLayout
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_details)
        val intenty:Intent = intent

        title = intenty.extras.getString("title")
        description = intenty.extras.getString("description")
        content = intenty.extras.getString("content")
        image = intenty.extras.getString("image")

        news_title = findViewById(R.id.newss_title)
        news_desc = findViewById(R.id.newss_description)
        news_content = findViewById(R.id.newss_content)
        news_image = findViewById(R.id.appbarnewsimg)
        collapsingToolbar = findViewById(R.id.collapsingnewstoolbar)
        toolbar = findViewById(R.id.newstoolbar)

        news_title.text = title
        news_desc.text = description
        news_content.text = content
        Picasso.get().load(image).into(news_image)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return false
    }
}