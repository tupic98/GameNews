package com.alvarenga.gamenews.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.alvarenga.gamenews.db.entity.NewsEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNew(news:NewsEntity)

    @Query("SELECT*FROM NewsEntity")
    fun getAllNews():LiveData<List<NewsEntity>>

    @Query("SELECT*FROM NewsEntity WHERE game=:game")
    fun getNewsByGame(game:String):LiveData<List<NewsEntity>>

    @Query("SELECT*FROM NewsEntity WHERE title=:title")
    fun getNewByTitle(title:String):LiveData<List<NewsEntity>>

    @Query("DELETE FROM NewsEntity")
    fun deleteAllFromTable()
}