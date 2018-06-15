package com.alvarenga.gamenews.gamenewsuca.api

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.alvarenga.gamenews.gamenewsuca.api.deserializer.TokenDeserializer
import com.alvarenga.gamenews.MainActivity
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException

object TokenRequest{
    fun login(user:String, password:String, activity:Activity, loginfields:RelativeLayout){
        loginfields.visibility = View.GONE
        val gson = GsonBuilder()
                .registerTypeAdapter(String::class.java,TokenDeserializer())
                .create() //Constuyendo o creando una instancia de GSon //Paramettros: clase y json en string
        val retrofit = Retrofit.Builder()
                .baseUrl(GameNewsucaAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson)).build() //Construyendo un nuevo retroit con la baseUrl
        val gameNewsucaAPI = retrofit.create<GameNewsucaAPI>(GameNewsucaAPI::class.java)
        val call = gameNewsucaAPI.login(user,password)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                if (response!!.isSuccessful) {
                    saveTokenOnPreferences(activity, response.body()!!)
                    startMainActivity(activity)
                }
                else{
                    when(response.code()){
                        401 -> {
                            loginfields.visibility = View.VISIBLE
                            Toast.makeText(activity,"Wrong Credentials",Toast.LENGTH_SHORT).show()
                        }
                        403 -> {
                            loginfields.visibility = View.VISIBLE
                            Toast.makeText(activity,response.message(),Toast.LENGTH_SHORT).show()
                        }
                        404 -> {
                            loginfields.visibility = View.VISIBLE
                            Toast.makeText(activity,response.message(),Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            loginfields.visibility = View.VISIBLE
                            Toast.makeText(activity,"Unknown error. We will check.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                if (t is SocketTimeoutException) {
                    loginfields.visibility = View.VISIBLE
                    Toast.makeText(activity, "Time out", Toast.LENGTH_SHORT).show()
                }else{
                    loginfields.visibility = View.VISIBLE
                    Toast.makeText(activity,"Unknown error. We will check.", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun saveTokenOnPreferences(context: Context, token:String) {
        val sharedpreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE) ?: return
        with(sharedpreferences.edit()){
            putString("token",token)
            apply()
        }
    }
    private fun startMainActivity(activity:Activity) {
        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
    }
}

/*
    val gameNewsAPI = retrofit.create(GameNewsAPI::class.java)
    val call = gameNewsAPI.login(user, password)
    call.enqueue(object:Callback<Login>() {
      fun onResponse(call:Call<Login>, response:Response<Login>) {
        if (response.isSuccessful() && response.body().isOKResponse())
        {
          Toast.makeText(context, "Exito", Toast.LENGTH_SHORT).show()
          saveToken(context, response.body().getToken())
          startMain(context)
        }
        else if (!response.body().isOKResponse())
        {
          relativeLayout.setVisibility(GONE)
          Toast.makeText(context, response.body().getToken(), Toast.LENGTH_SHORT).show()
        }
        else
        {
          relativeLayout.setVisibility(GONE)
          Toast.makeText(context, "Something weird happend", Toast.LENGTH_SHORT).show()
        }
      }
      fun onFailure(call:Call<Login>, t:Throwable) {
        if (t is SocketTimeoutException)
        {
          Toast.makeText(context, "Time out bitch", Toast.LENGTH_SHORT).show()
        }
      }
    })
  }

  fun fetchAllNews(context:Context, viewModel:NewsViewModel, aux:String) {
    val retrofit = Retrofit.Builder()
    .baseUrl(GameNewsAPI.ENDPOINT)
    .addConverterFactory(GsonConverterFactory.create(Gson()))
    .build()
    val service = retrofit.create(GameNewsAPI::class.java)
    val news = service.getNews("Beared " + aux)
    news.enqueue(object:Callback<List<New>>() {
      fun onResponse(call:Call<List<New>>, response:Response<List<New>>) {
        if (response.isSuccessful())
        {
          setListNewEntity(response.body(), viewModel)
          Toast.makeText(context, "Fetching data", Toast.LENGTH_SHORT).show()
        }
        else
        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
      }
      fun onFailure(call:Call<List<New>>, t:Throwable) {
        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
      }
    })
  }
  private fun setListNewEntity(list:List<New>, viewModel:NewsViewModel) {
    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    try
    {
      for (li in list)
      {
        li.setCreatedDate(df.parse(li.getCreated_date()))
      }
    }
    catch (e:Exception) {
      e.printStackTrace()
    }
    Collections.sort(list)
    Collections.reverse(list)
    for (x in list)
    {
      val newEntity = NewEntity(
        x.get_id(), x.getTitle(), x.getCoverImage(), x.getDescription(),
        x.getBody(), x.getGame(), x.getCreated_date()
      )
      viewModel.insert(newEntity)
    }
  }
  fun getCategories(aux:String, viewModel:CategoryViewModel) {
    val gson = GsonBuilder()
    .registerTypeAdapter(ArrayList::class.java, CategoriesDeserializer())
    .create()
    val retrofit = Retrofit.Builder()
    .baseUrl(GameNewsAPI.ENDPOINT)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()
    val service = retrofit.create(GameNewsAPI::class.java)
    val categories = service.getCategories("Beared " + aux)
    categories.enqueue(object:Callback<List<String>>() {
      fun onResponse(call:Call<List<String>>, response:Response<List<String>>) {
        System.out.println(response.code())
        if (response.body() != null)
        {
          for (x in response.body())
          {
            viewModel.insertCategory(CategoryEntity(x))
          }
        }
      }
      fun onFailure(call:Call<List<String>>, t:Throwable) {
      }
    })
  }
}*/