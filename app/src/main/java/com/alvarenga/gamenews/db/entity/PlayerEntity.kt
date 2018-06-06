package com.alvarenga.gamenews.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity
data class PlayerEntity(@NonNull @PrimaryKey @ColumnInfo(name = "_id") var id:String = "",
                        @ColumnInfo(name = "name") var name:String,
                        @ColumnInfo(name = "biografia") var biografia:String,
                        @ColumnInfo(name = "avatar") var avatar:String,
                        @ColumnInfo(name = "game") var game:String)