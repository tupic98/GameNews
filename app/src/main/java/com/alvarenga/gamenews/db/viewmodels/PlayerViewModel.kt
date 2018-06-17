package com.alvarenga.gamenews.db.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.alvarenga.gamenews.db.entity.PlayerEntity
import com.alvarenga.gamenews.db.repository.PlayerRepository

class PlayerViewModel(app:Application):AndroidViewModel(app){
    internal var playerRepository:PlayerRepository
    init {
        playerRepository = PlayerRepository(app)
    }

    fun insertPlayer(playerEntity: PlayerEntity){
        playerRepository.insertPlayer(playerEntity)
    }

    fun getPlayer(game:String):LiveData<List<PlayerEntity>>{
        return playerRepository.getPlayer(game)
    }
}