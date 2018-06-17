package com.alvarenga.gamenews.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alvarenga.gamenews.R
import com.alvarenga.gamenews.adapters.CatViewPagerAdapter
import kotlinx.android.synthetic.main.game_category_fragment.*

@SuppressLint("ValidFragment", "StaticFieldLeak")
class PerGameFragment:Fragment(){
    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager:ViewPager
    internal lateinit var context:Context
    lateinit var activity:Activity
    private lateinit var category:String
    private lateinit var adapter:CatViewPagerAdapter

    fun newInstance(category:String):PerGameFragment{
        val fragment = PerGameFragment()
        val args = Bundle()
        args.putString("category",category)
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category=arguments!!.getString("category")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.game_category_fragment,container,false)
        adapter = CatViewPagerAdapter(childFragmentManager)
        viewPager = view.findViewById(R.id.Pager)
        tabLayout = view.findViewById(R.id.mainTab)
        adapter.addFragment(NewsFragment().newInstance(1,category),"News")
        adapter.addFragment(TopPlayerFragment().newInstance(category),"Top Players")
        adapter.addFragment(ImagesFragment().newInstance(category),"Images")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        return view
    }

}