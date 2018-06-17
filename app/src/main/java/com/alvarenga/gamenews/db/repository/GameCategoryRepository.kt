package com.alvarenga.gamenews.db.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.alvarenga.gamenews.db.AppDatabase
import com.alvarenga.gamenews.db.dao.GameCategoryDao
import com.alvarenga.gamenews.db.entity.GameCategoryEntity
import com.alvarenga.gamenews.db.entity.NewsEntity
import com.alvarenga.gamenews.gamenewsuca.api.GameNewsucaAPI
import com.alvarenga.gamenews.gamenewsuca.api.deserializer.GameCaregoryDeserializer
import com.alvarenga.gamenews.gamenewsuca.api.deserializer.NewsDeserializer
import com.alvarenga.gamenews.gamenewsuca.api.models.News
import com.google.gson.GsonBuilder
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException

class GameCategoryRepository(app:Application){
    var gameCategoryDao:GameCategoryDao? = null
    var logintoken:String? = null
    var context: Context? = null

    init{
        val database = AppDatabase.getInstanceDatabase(app)
        gameCategoryDao = database!!.gameCategoryDao()
        val sharedPreferences = app.getSharedPreferences("Login", Context.MODE_PRIVATE)
        logintoken = sharedPreferences.getString("token","")
        doGetCategoriesFromAPI()
    }

    fun doGetCategoriesFromAPI(){
        val gson = GsonBuilder()
                .registerTypeAdapter(ArrayList::class.java,GameCaregoryDeserializer())
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl(GameNewsucaAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val gameNewsucaAPI = retrofit.create<GameNewsucaAPI>(GameNewsucaAPI::class.java)
        val call = gameNewsucaAPI.categories("Bearer " + logintoken)
        call.enqueue(object : Callback<List<String>>{
            override fun onFailure(call: Call<List<String>>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<List<String>>?, response: Response<List<String>>?) {
                if(response!!.isSuccessful){
                    Log.d("Successful","Fetching data")
                    for(cateogries in response.body()!!){
                        insertCategory(GameCategoryEntity(cateogries))
                    }
                }
            }
        })
    }

    fun insertCategory(gameCategories:GameCategoryEntity){
        doAsync {
            gameCategoryDao!!.insertGame(gameCategories)
        }
    }

    fun getAllGames():LiveData<List<GameCategoryEntity>>{
        return gameCategoryDao!!.getAllGames()
    }

}