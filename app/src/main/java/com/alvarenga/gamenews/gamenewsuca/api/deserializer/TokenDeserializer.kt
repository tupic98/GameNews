package com.alvarenga.gamenews.gamenewsuca.api.deserializer

import com.google.gson.*
import java.lang.reflect.Type

class TokenDeserializer:JsonDeserializer<String>{
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): String {
        lateinit var logintoken:String
        if(json!!.asJsonObject != null){
            var LoginJsonObject:JsonObject = json.asJsonObject
            if(LoginJsonObject.get("token") != null){
                logintoken = LoginJsonObject.get("token")!!.asString
            }
        }
        return logintoken
    }
}
