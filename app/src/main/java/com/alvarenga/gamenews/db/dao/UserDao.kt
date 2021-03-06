package com.alvarenga.gamenews.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.alvarenga.gamenews.db.entity.UserEntity

@Dao
interface UserDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg user:UserEntity)

    @Query("SELECT*FROM UserEntity WHERE user like :username")
    fun getUserByUsername(username:String):UserEntity
}