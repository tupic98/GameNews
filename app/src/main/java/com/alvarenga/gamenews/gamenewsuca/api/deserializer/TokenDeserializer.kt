package com.alvarenga.gamenews.gamenewsuca.api.deserializer

import com.alvarenga.gamenews.gamenewsuca.api.models.Login
import com.google.gson.*
import java.lang.reflect.Type

class TokenDeserializer:JsonDeserializer<Login>{
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Login {
        var logintoken = Login()
        if(json!!.asJsonObject != null){
            var LoginJsonObject:JsonObject = json.asJsonObject
            if(LoginJsonObject.get("token") != null){
                logintoken.token = LoginJsonObject.get("token")!!.asString
                logintoken.isResponseOk = true;
            }else{
                logintoken.isResponseOk = false
                logintoken.token = LoginJsonObject.get("message")!!.asString
            }
        }
        return logintoken
    }
}
