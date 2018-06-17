package com.alvarenga.gamenews.db.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.alvarenga.gamenews.db.entity.GameCategoryEntity
import com.alvarenga.gamenews.db.repository.GameCategoryRepository

class GameCategoryViewModel(app:Application):AndroidViewModel(app){
    internal var categoryRepository:GameCategoryRepository
    init{
        categoryRepository = GameCategoryRepository(app)
    }

    fun getCategories():LiveData<List<GameCategoryEntity>>{
        return categoryRepository.getAllGames()
    }

    fun insertRepositorie(game:GameCategoryEntity){
        categoryRepository.insertCategory(game)
    }
}