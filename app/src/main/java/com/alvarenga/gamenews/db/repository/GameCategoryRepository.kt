package com.alvarenga.gamenews.db.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Context
import com.alvarenga.gamenews.db.AppDatabase
import com.alvarenga.gamenews.db.dao.GameCategoryDao
import com.alvarenga.gamenews.db.entity.GameCategoryEntity
import org.jetbrains.anko.doAsync

class GameCategoryRepository(app:Application){
    var gameCategoryDao:GameCategoryDao? = null
    var logintoken:String? = null
    var context: Context? = null

    init{
        val database = AppDatabase.getInstanceDatabase(app)
        gameCategoryDao = database!!.gameCategoryDao()
        val sharedPreferences = app.getSharedPreferences("Login", Context.MODE_PRIVATE)
        logintoken = sharedPreferences.getString("token","")
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