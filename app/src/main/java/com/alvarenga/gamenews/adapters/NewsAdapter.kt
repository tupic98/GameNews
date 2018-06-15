package com.alvarenga.gamenews.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.alvarenga.gamenews.R
import com.alvarenga.gamenews.db.entity.NewsEntity
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso

class NewsAdapter(private val context:Context):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var listNews: List<NewsEntity>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_cardview, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        var news:NewsEntity = listNews!![position]

        holder.title.text = news.title
        holder.desc.text = news.description

        Picasso.get().load(news.coverImage)
                .resize(360,115)
                .centerCrop()
                .error(R.drawable.ic_menu_gallery)
                .into(holder.coverImage)
    }

    fun setNews(news:List<NewsEntity>){
        listNews = news
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (listNews == null){
            return 0;
        }else {
            return listNews!!.size
        }
    }
    class NewsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var title:TextView = itemView.findViewById(R.id.NewsTitle)
        var desc:TextView = itemView.findViewById(R.id.NewsDesc)
        var coverImage:ImageView = itemView.findViewById(R.id.NewsImage)/*
        var favoriteButton:ImageButton = itemView.findViewById(R.id.FavNewsImage)*/


    }
}