package com.alvarenga.gamenews.gamenewsuca.api.deserializer

import com.alvarenga.gamenews.gamenewsuca.api.models.News
import com.google.gson.*
import java.lang.reflect.Type

class NewsDeserializer:JsonDeserializer<News>{
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): News {
        val news = News()
        if(json!!.asJsonObject != null){
            val newsJasonObject:JsonObject = json.asJsonObject

            if(newsJasonObject.get("_id") != null)
                news.id = newsJasonObject.get("_id").asString
            else news.id = ""

            if(newsJasonObject.get("title") != null)
                news.title = newsJasonObject.get("title").asString
            else news.title = ""

            if(newsJasonObject.get("coverImage") != null)
                news.coverImage = newsJasonObject.get("coverImage").asString
            else news.coverImage = ""

            if(newsJasonObject.get("create_date") != null)
                news.date = newsJasonObject.get("create_date").asString
            else news.date = ""

            if(newsJasonObject.get("description") != null)
                news.description = newsJasonObject.get("description").asString
            else news.description = ""

            if(newsJasonObject.get("body") != null)
                news.body = newsJasonObject.get("body").asString
            else news.body = ""

            if(newsJasonObject.get("game") != null)
                news.game = newsJasonObject.get("game").asString
            else news.game = ""
        }
        return news
    }
}