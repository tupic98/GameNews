package com.alvarenga.gamenews.db.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.SharedPreferences
import com.alvarenga.gamenews.db.AppDatabase
import com.alvarenga.gamenews.db.dao.NewsDao
import com.alvarenga.gamenews.db.entity.NewsEntity
import com.alvarenga.gamenews.gamenewsuca.api.GameNewsucaAPI
import com.alvarenga.gamenews.gamenewsuca.api.deserializer.NewsDeserializer
import com.alvarenga.gamenews.gamenewsuca.api.models.News
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.android.volley.NetworkResponse
import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.experimental.android.UI
import okhttp3.OkHttpClient
import org.jetbrains.anko.doAsync
import java.net.SocketTimeoutException
import java.sql.Time
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class NewsRepository constructor(app:Application) {

    var newsDao:NewsDao
    var logintoken:String
    val context: Context? = null
    lateinit var sharedPreferences: SharedPreferences

    init {
        val database = AppDatabase.getInstanceDatabase(app)
        newsDao = database!!.newsDao()
        sharedPreferences = app.getSharedPreferences("Login", Context.MODE_PRIVATE)
        logintoken = sharedPreferences.getString("token","")
        NewsRequestFromAPI()
    }

    fun NewsRequestFromAPI(){
        val gson = GsonBuilder()
                .registerTypeAdapter(News::class.java,NewsDeserializer())
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl(GameNewsucaAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val gameNewsucaAPI = retrofit.create<GameNewsucaAPI>(GameNewsucaAPI::class.java)
        val call = gameNewsucaAPI.news("Bearer " + logintoken)
        call.enqueue(object : Callback<List<News>>{
            override fun onFailure(call: Call<List<News>>?, t: Throwable?) {
                if(t is SocketTimeoutException)
                    Toast.makeText(context,"Timed out",Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<News>>?, response: Response<List<News>>?) {
                if(response!!.isSuccessful){
                    Log.d("Successful","Fetching data")
                    for (news in response!!.body()!!){
                        insertNew(NewsEntity(news.id,news.title.trimEnd(),news.coverImage,news.date,news.description.trimEnd(),news.body,news.game))
                        insertNew(NewsEntity(news.id,news.title.trimEnd(),news.coverImage,news.date,news.description.trimEnd(),news.body,news.game,0))
                    }
                }
                when(response.code()){
                    401 -> {
                        with(sharedPreferences.edit()){
                            clear()
                            apply()
                        }
                        Toast.makeText(context,"Session expired",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }


    fun insertNew(newsEntity: NewsEntity){
        doAsync {
            newsDao.insertNew(newsEntity)
        }
    }

    fun getAllNews():LiveData<List<NewsEntity>>{
        return newsDao.getAllNews()
    }

    fun getNewsByGame(game:String):LiveData<List<NewsEntity>>{
        return newsDao.getNewsByGame(game)
    }

    fun getNewByTitle(title:String):LiveData<List<NewsEntity>>{
        return newsDao.getNewByTitle(title)
    }

    fun deleteAllNews(){
        doAsync {
            newsDao.deleteAllFromTable()
        }
    }

    fun deleteFav(id:String){
        doAsync {
            newsDao.deleteFav(id)
        }

    }

    fun getNewsByQuery(query:String):LiveData<List<NewsEntity>>{
        return newsDao.getNewByQuery(query)
    }

}