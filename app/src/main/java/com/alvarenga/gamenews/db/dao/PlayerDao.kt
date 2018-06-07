package com.alvarenga.gamenews.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.alvarenga.gamenews.db.entity.PlayerEntity

@Dao
interface PlayerDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(PlayerEntity:PlayerEntity)
    /*@Query("SELECT*FROM PlayerEntity WHERE game=:game")
    fun getPlayer(game:String):LiveData<List<PlayerEntity>>*/
}