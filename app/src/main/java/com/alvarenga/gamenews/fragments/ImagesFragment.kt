package com.alvarenga.gamenews.fragments

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alvarenga.gamenews.R
import com.alvarenga.gamenews.adapters.ImagesAdapter
import com.alvarenga.gamenews.db.entity.NewsEntity
import com.alvarenga.gamenews.db.viewmodels.NewsViewModel


@SuppressLint("StaticFieldLeak")
class ImagesFragment: Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsVM:NewsViewModel
    private lateinit var category: String
    private lateinit var adapter: ImagesAdapter
    private lateinit var logintoken:String

    fun newInstance(category: String):ImagesFragment{
        val args = Bundle()
        val fragment = ImagesFragment()
        args.putString("category", category)
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments!!.getString("category")
        newsVM = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        val sharedPreferences: SharedPreferences = activity!!.getSharedPreferences("Login", Context.MODE_PRIVATE)
        logintoken = sharedPreferences.getString("token","")
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.news_fragment,container,false)
        adapter = ImagesAdapter()
        newsVM.getNewsByGame(category).observe(this, Observer<List<NewsEntity>>{ t -> adapter.setNewsListForImages(t!!)})
        recyclerView = view.findViewById(R.id.NewsRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context,3)
        return view
    }
}