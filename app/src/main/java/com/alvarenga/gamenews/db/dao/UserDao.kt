package com.alvarenga.gamenews.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.alvarenga.gamenews.db.entity.UserEntity

@Dao
interface UserDao{
    @Insert
    fun insertUser(user:UserEntity)

    @Query("SELECT*FROM UserEntity WHERE user like :username")
    fun getUserByUsername(username:String):UserEntity
}