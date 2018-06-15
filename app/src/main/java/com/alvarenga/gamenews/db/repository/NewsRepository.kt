package com.alvarenga.gamenews.db.repository

import android.app.Application
import android.arch.lifecycle.LiveData
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
import kotlinx.coroutines.experimental.android.UI
import org.jetbrains.anko.doAsync
import java.net.SocketTimeoutException
import java.util.*


class NewsRepository(app:Application) {

    var newsDao:NewsDao
    var logintoken:String
    val context: Context? = null
    lateinit var sharedPreferences: SharedPreferences

    init {
        val database = AppDatabase.getInstanceDatabase(app)
        newsDao = database!!.newsDao()
        var sharedPreferences = app.getSharedPreferences("Login", Context.MODE_PRIVATE)
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
/*    private class InsertAsyncTask internal constructor(private val AsyncTaskNewsDAO: NewsDao) : AsyncTask<NewsEntity, Void, Void>() {

        override fun doInBackground(vararg newsEntities: NewsEntity): Void? {
            AsyncTaskNewsDAO.insertNew(newsEntities[0])
            return null
        }
    }*/

    /*    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNew(news:NewsEntity)

    @Query("SELECT*FROM NewsEntity")
    fun getAllNews():LiveData<List<NewsEntity>>

    @Query("SELECT*FROM NewsEntity WHERE game=:game")
    fun getNewsByGame(game:String):LiveData<List<NewsEntity>>

    @Query("SELECT*FROM NewsEntity WHERE title like :query")
    fun getNewByQuery(query:String):LiveData<List<NewsEntity>>*/
}