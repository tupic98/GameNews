package com.alvarenga.gamenews.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.alvarenga.gamenews.db.entity.CategoryEntity

@Dao
interface CategoryDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category:CategoryEntity)

    @Query("SELECT*FROM CategoryEntity")
    fun getAllCategories():LiveData<List<CategoryEntity>>
}