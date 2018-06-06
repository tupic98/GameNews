package com.alvarenga.gamenews.db.entity

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import android.support.annotation.NonNull

@Entity(indices = [
            Index("news_id"),
            Index("user_id")],
        foreignKeys = [
            ForeignKey(entity = UserEntity::class,
            parentColumns = ["_id"],
            childColumns = ["user_id"],
            onDelete = CASCADE)],
        primaryKeys = ["user_id","new_id"])
data class FavNewsEntity(@NonNull @ColumnInfo(name = "user_id") var userID:String="",
                         @NonNull @ColumnInfo(name = "news_id") var newsID:String="")
