package com.alvarenga.gamenews.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.alvarenga.gamenews.db.entity.GameCategoryEntity

@Dao
interface GameCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(GameCategory:GameCategoryEntity)

    @Query("SELECT * FROM GameCategoryEntity")
    fun getAllGames():LiveData<List<GameCategoryEntity>>
}