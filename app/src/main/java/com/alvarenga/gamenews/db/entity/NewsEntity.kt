package com.alvarenga.gamenews.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.FileDescriptor

@Entity
data class NewsEntity(@PrimaryKey @ColumnInfo(name = "_id") var id:String ="",
                      @ColumnInfo(name = "title") var title:String = "",
                      @ColumnInfo(name = "coverImage") var coverImage:String = "",
                      @ColumnInfo(name = "create_date") var createDate:String = "",
                      @ColumnInfo(name = "description") var description:String = "",
                      @ColumnInfo(name = "body") var body:String = "",
                      @ColumnInfo(name = "game") var game:String = "")