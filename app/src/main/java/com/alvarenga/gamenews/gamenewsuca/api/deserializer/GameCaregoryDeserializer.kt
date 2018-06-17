package com.alvarenga.gamenews.gamenewsuca.api.deserializer

import com.google.gson.*
import java.lang.reflect.Type

class GameCaregoryDeserializer:JsonDeserializer<List<String>>{
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): List<String> {
        var gamecategories:ArrayList<String> = ArrayList()
        val arrayofCategories:JsonArray = json!!.asJsonArray
        for(game in arrayofCategories){
            gamecategories.add(game.asString)
        }
        return gamecategories
    }
}