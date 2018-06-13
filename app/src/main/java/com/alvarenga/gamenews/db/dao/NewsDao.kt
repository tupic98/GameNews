package com.alvarenga.gamenews.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.alvarenga.gamenews.db.entity.NewsEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNew(news:NewsEntity)

    @Update
    fun updateNew(news:NewsEntity)

    @Query("SELECT*FROM NewsEntity")
    fun getAllNews():LiveData<List<NewsEntity>>

    @Query("SELECT*FROM NewsEntity WHERE game=:game")
    fun getNewsByGame(game:String):LiveData<List<NewsEntity>>

    @Query("SELECT*FROM NewsEntity WHERE title like :query")
    fun getNewByQuery(query:String):LiveData<List<NewsEntity>>
}