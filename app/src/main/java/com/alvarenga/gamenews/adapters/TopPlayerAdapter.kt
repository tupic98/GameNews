package com.alvarenga.gamenews.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.alvarenga.gamenews.R
import com.alvarenga.gamenews.db.entity.PlayerEntity
import com.squareup.picasso.Picasso

class TopPlayerAdapter:RecyclerView.Adapter<TopPlayerAdapter.PlayerViewHolder>(){

    private var playerList:List<PlayerEntity>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.top_player_cardview,parent,false)
        return PlayerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if(playerList == null) 0 else playerList!!.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val players:PlayerEntity = playerList!![position]
        holder.name.text = players.name
        Picasso.get().load(players.avatar).error(R.drawable.ic_menu_gallery).into(holder.image)
    }

    class PlayerViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val image:ImageView = itemView.findViewById(R.id.top_player_image)
        val name:TextView = itemView.findViewById(R.id.top_player_name)
    }

    fun setPlayers(list:List<PlayerEntity>){
        playerList = list
        notifyDataSetChanged()
    }

}