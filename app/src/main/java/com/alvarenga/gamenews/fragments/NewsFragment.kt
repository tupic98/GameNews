package com.alvarenga.gamenews.fragments

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alvarenga.gamenews.R
import com.alvarenga.gamenews.adapters.NewsAdapter
import com.alvarenga.gamenews.db.entity.NewsEntity
import com.alvarenga.gamenews.db.repository.NewsRepository
import com.alvarenga.gamenews.db.viewmodels.NewsViewModel
import com.alvarenga.gamenews.extras.Utils
import com.alvarenga.gamenews.gamenewsuca.api.models.News

class NewsFragment(): Fragment() {
    private lateinit var newsVM:NewsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var logintoken:String
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var mLayoutManagerState:Parcelable

    override fun onPause() {
        super.onPause()
        mLayoutManagerState = recyclerView.layoutManager.onSaveInstanceState()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var sharedPreferences:SharedPreferences = activity!!.getSharedPreferences("Login",Context.MODE_PRIVATE)
        logintoken = sharedPreferences.getString("token","")
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        newsVM = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        val view:View = inflater.inflate(R.layout.news_fragment,container,false)
        recyclerView = view.findViewById(R.id.NewsRecyclerView)
        gridLayoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int
                    = if (position % 3 == 0) 2 else 1
        }
        newsAdapter = NewsAdapter(context!!)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = newsAdapter
        newsVM.getAllNews().observe(this,Observer<List<NewsEntity>>{t -> newsAdapter.setNews(t!!) })

        if (savedInstanceState != null) {
            mLayoutManagerState = savedInstanceState.getParcelable("LAYOUT_MANAGER_STATE")
            recyclerView.layoutManager.onRestoreInstanceState(mLayoutManagerState)
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("LAYOUT_MANAGER_STATE", mLayoutManagerState)
    }



    private fun orderingNewsLists(new:List<News>):List<News>{
        return new
    }

}