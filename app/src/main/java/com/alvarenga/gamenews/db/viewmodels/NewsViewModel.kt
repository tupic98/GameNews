package com.alvarenga.gamenews.db.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.alvarenga.gamenews.db.entity.NewsEntity
import com.alvarenga.gamenews.db.repository.NewsRepository
import com.alvarenga.gamenews.gamenewsuca.api.models.News

class NewsViewModel(app:Application):AndroidViewModel(app){
    internal var newsRepository:NewsRepository
    init{
        newsRepository = NewsRepository(app)
    }

    fun insertNew(newsEntity: NewsEntity){
        newsRepository.insertNew(newsEntity)
    }

    fun getAllNews():LiveData<List<NewsEntity>>{
        return newsRepository.getAllNews()
    }

    fun getNewsByGame(game:String):LiveData<List<NewsEntity>>{
        return newsRepository.getNewsByGame(game)
    }

    fun getNewByTitle(title:String):LiveData<List<NewsEntity>>{
        return newsRepository.getNewByTitle(title)
    }

    fun getNewsByQuery(query:String):LiveData<List<NewsEntity>>{
        return newsRepository.getNewsByQuery(query)
    }

    fun deleteAllNews(){
        newsRepository.deleteAllNews()
    }
    
}