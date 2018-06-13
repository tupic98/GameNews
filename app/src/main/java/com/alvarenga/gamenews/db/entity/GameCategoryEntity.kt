package com.alvarenga.gamenews.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity
data class GameCategoryEntity(@NonNull @PrimaryKey @ColumnInfo(name = "game_category_name") var cateogryname:String = "")