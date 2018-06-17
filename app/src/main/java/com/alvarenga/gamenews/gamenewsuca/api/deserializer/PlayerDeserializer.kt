package com.alvarenga.gamenews.gamenewsuca.api.deserializer

import com.alvarenga.gamenews.gamenewsuca.api.models.Player
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class PlayerDeserializer : JsonDeserializer<Player>{
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Player {
        val player:Player = Player()
        if(json!!.asJsonObject != null){
            val playerList:JsonObject = json.asJsonObject
            player.id = playerList.get("_id").asString
            player.avatar = playerList.get("avatar").asString
            player.biografia = playerList.get("biografia").asString
            player.game = playerList.get("game").asString
            player.name = playerList.get("name").asString
        }
        return player
    }
}