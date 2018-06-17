package com.alvarenga.gamenews.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.alvarenga.gamenews.R
import com.alvarenga.gamenews.db.entity.NewsEntity
import com.squareup.picasso.Picasso

class ImagesAdapter: RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {
    private var newsList: List<NewsEntity>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val view:View = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.images_cardview,parent,false)
        return ImagesViewHolder(view)

    }

    override fun getItemCount(): Int {
        return if (newsList == null) 0 else newsList!!.size
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val new:NewsEntity = newsList!![position]
        Picasso.get().load(new.coverImage)
                .error(R.drawable.ic_menu_gallery)
                .into(holder.images)
    }

    class ImagesViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val images:ImageView = itemView.findViewById<ImageView>(R.id.games_image_cardview)
    }

    fun setNewsListForImages(list:List<NewsEntity>){
        newsList = list
        notifyDataSetChanged()
    }

}