package com.alvarenga.gamenews.extras

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

class Utils{
    companion object {
        fun CheckNetConnection(activity: Activity):Boolean{
            val ConMan: ConnectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetStatus = ConMan.activeNetworkInfo
            return activeNetStatus != null && activeNetStatus.isConnected
        }
    }
}