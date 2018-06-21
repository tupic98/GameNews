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

abstract class NewsAdapter():RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var listNews: List<NewsEntity>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_cardview, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        var news:NewsEntity = listNews!![position]

        holder.NewsId = news.id
        holder.title.text = news.title
        holder.desc.text = news.description
        holder.currentFav = news.favorite
        holder.favBurro.setImageResource(if (holder.currentFav == 0) R.drawable.ic_fav_border_black_24dp else R.drawable.ic_fav_black_24dp)
        Picasso.get().load(news.coverImage)
                .resize(360,115)
                .centerCrop()
                .error(R.drawable.ic_menu_gallery)
                .into(holder.coverImage)
        holder.titulo = news.title
        holder.description = news.description
        holder.content = news.body
        holder.image = news.coverImage/*
        holder.favBurro.setOnClickListener{v -> onClickFav(v,holder.NewsId,holder.currentFav) }*/
        holder.itemView.setOnClickListener{v ->  onClickCardView(holder.titulo,holder.description,holder.content,holder.image)}
    }


    abstract fun onClickFav(view:View,id:String,currentFav:Int)

    abstract fun onClickCardView(titulo:String,description:String,content:String,image:String)

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
        var NewsId:String = ""
        var currentFav:Int = 0
        lateinit var image:String
        lateinit var titulo:String
        lateinit var description:String
        lateinit var content:String
        var title:TextView = itemView.findViewById(R.id.NewsTitle)
        var desc:TextView = itemView.findViewById(R.id.NewsDesc)
        var coverImage:ImageView = itemView.findViewById(R.id.NewsImage)
        var favBurro:ImageButton = itemView.findViewById(R.id.imageButton)
    }
}