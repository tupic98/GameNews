package com.alvarenga.gamenews.adapters

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter

class CatViewPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm){

    private var titleList:ArrayList<String> = ArrayList()
    private var fragmentList:ArrayList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun addFragment(fragment: Fragment,title:String){
        fragmentList.add(fragment)
        titleList.add(title)
    }
}