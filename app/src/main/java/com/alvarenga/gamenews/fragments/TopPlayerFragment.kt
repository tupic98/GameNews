package com.alvarenga.gamenews.fragments

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alvarenga.gamenews.R
import com.alvarenga.gamenews.adapters.TopPlayerAdapter
import com.alvarenga.gamenews.db.viewmodels.PlayerViewModel

class TopPlayerFragment: Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var category:String
    private lateinit var logintoken:String
    private lateinit var adapter:TopPlayerAdapter
    private lateinit var playerVM:PlayerViewModel

    fun newInstance(category:String):TopPlayerFragment{
        val args = Bundle()
        val fragment = TopPlayerFragment()
        args.putString("category", category)
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerVM = ViewModelProviders.of(this).get(PlayerViewModel::class.java)
        category = arguments!!.getString("category")
        val SharedPref:SharedPreferences = activity!!.getSharedPreferences("Login",Context.MODE_PRIVATE)
        logintoken = SharedPref.getString("token","")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.news_fragment,container,false)
        recyclerView = view.findViewById(R.id.NewsRecyclerView)
        adapter = TopPlayerAdapter()
        playerVM.getPlayer(category).observe(this, Observer { t -> adapter.setPlayers(t!!) })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }
}