package com.alvarenga.gamenews.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import com.alvarenga.gamenews.db.entity.FavNewsEntity

@Dao
interface FavNewsDao{
    @Insert
    fun insertFavNews(favnews:FavNewsEntity)
}