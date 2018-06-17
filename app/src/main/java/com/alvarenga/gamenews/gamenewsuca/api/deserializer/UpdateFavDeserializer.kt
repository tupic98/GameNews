package com.alvarenga.gamenews.gamenewsuca.api.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class UpdateFavDeserializer:JsonDeserializer<String>{
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): String {
        return json!!.asJsonObject.get("success").asString
    }
}