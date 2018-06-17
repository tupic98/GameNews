package com.alvarenga.gamenews.db.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.alvarenga.gamenews.db.AppDatabase
import com.alvarenga.gamenews.db.dao.PlayerDao
import com.alvarenga.gamenews.db.entity.NewsEntity
import com.alvarenga.gamenews.db.entity.PlayerEntity
import com.alvarenga.gamenews.gamenewsuca.api.GameNewsucaAPI
import com.alvarenga.gamenews.gamenewsuca.api.deserializer.NewsDeserializer
import com.alvarenga.gamenews.gamenewsuca.api.deserializer.PlayerDeserializer
import com.alvarenga.gamenews.gamenewsuca.api.models.News
import com.alvarenga.gamenews.gamenewsuca.api.models.Player
import com.google.gson.GsonBuilder
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException

class PlayerRepository(app:Application){
    lateinit var playerDao: PlayerDao
    lateinit var logintoken:String
    val context:Context? = null

    init {
        val database = AppDatabase.getInstanceDatabase(app)
        playerDao = database!!.playerDao()
        val sharedPref = app.getSharedPreferences("Login",Context.MODE_PRIVATE)
        logintoken = sharedPref.getString("token","")
        PlayerRequestFromAPI()
    }

    fun PlayerRequestFromAPI(){
        val gson = GsonBuilder()
                .registerTypeAdapter(Player::class.java, PlayerDeserializer() )
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl(GameNewsucaAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val gameNewsucaAPI = retrofit.create<GameNewsucaAPI>(GameNewsucaAPI::class.java)
        val call = gameNewsucaAPI.players("Bearer " + logintoken)
        call.enqueue(object : Callback<List<Player>> {
            override fun onFailure(call: Call<List<Player>>?, t: Throwable?) {
                if(t is SocketTimeoutException)
                    Toast.makeText(context,"Timed out", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<Player>>?, response: Response<List<Player>>?) {
                if(response!!.isSuccessful){
                    Log.d("Successful","Fetching data")
                    for (player in response!!.body()!!){
                        insertPlayer(PlayerEntity(player.id,player.name,player.biografia,player.avatar,player.game))
                    }
                }

            }
        })
    }

    fun insertPlayer(playerEntity: PlayerEntity){
        doAsync {
            playerDao.insertPlayer(playerEntity)
        }
    }

    fun getPlayer(game:String):LiveData<List<PlayerEntity>>{
        return playerDao.getPlayer(game)
    }
}