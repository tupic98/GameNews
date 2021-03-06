package com.alvarenga.gamenews.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.provider.CalendarContract
import com.alvarenga.gamenews.db.dao.*
import com.alvarenga.gamenews.db.entity.*

@Database(
        entities = [NewsEntity::class,UserEntity::class,PlayerEntity::class, GameCategoryEntity::class],
        exportSchema = false,
        version = 1
)
abstract class AppDatabase:RoomDatabase(){

    abstract fun newsDao():NewsDao
    abstract fun userDao():UserDao
    abstract fun playerDao():PlayerDao
    abstract fun gameCategoryDao():GameCategoryDao

    companion object {
        private var INSTANCE:AppDatabase? = null
        private val DBNAME:String = "gamenews.db"

        fun getInstanceDatabase(context: Context):AppDatabase?{
            if(INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, DBNAME)
                            .build()
                }
            }
            return INSTANCE
        }
    }

}