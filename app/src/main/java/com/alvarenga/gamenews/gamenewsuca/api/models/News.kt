package com.alvarenga.gamenews.gamenewsuca.api.models

import com.google.gson.annotations.SerializedName
import java.util.*

class News:Comparable<News>{
    @SerializedName("_id")
    private lateinit var id:String
    private lateinit var title:String
    private lateinit var body:String
    private lateinit var game:String
    @SerializedName("created_date")
    private lateinit var date:String
    private lateinit var coverImage:String
    private lateinit var createdDate:Date

    override fun toString(): String {
        return title + "--" + game
    }

    override fun compareTo(other: News): Int {
        return createdDate.compareTo(other.createdDate)
    }

}
