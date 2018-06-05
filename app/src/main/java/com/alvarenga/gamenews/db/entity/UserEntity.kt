package com.alvarenga.gamenews.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class UserEntity(@PrimaryKey @ColumnInfo(name = "_id") var id:String = "",
                      @ColumnInfo(name = "user") var user:String = "",
                      @ColumnInfo(name = "password") var password:String = "")
