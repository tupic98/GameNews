package com.alvarenga.gamenews.gamenewsuca.api

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentManager
import android.widget.Toast
import com.alvarenga.gamenews.MainActivity
import com.alvarenga.gamenews.SignInDialog
import com.alvarenga.gamenews.gamenewsuca.api.deserializer.TokenDeserializer
import com.alvarenga.gamenews.gamenewsuca.api.models.Login
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException

object APIRequest{
    fun login(user:String, password:String, activity:Activity, fragmentManager:FragmentManager){
        var signinDialog = SignInDialog() //Instanciando Dialogo
        signinDialog.show(fragmentManager,"Sign In") //Mostrando Dialogo
        val gson = GsonBuilder()
                .registerTypeAdapter(Login::class.java,TokenDeserializer())
                .create() //Constuyendo o creando una instancia de GSon //Paramettros: clase y json en string
        val builder = Retrofit.Builder()
                .baseUrl(GameNewsucaAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
        val retrofit = builder.build() //Construyendo un nuevo retroit con la baseUrl
        val gameNewsucaAPI = retrofit.create<GameNewsucaAPI>(GameNewsucaAPI::class.java)
        val call = gameNewsucaAPI.login(user,password)
        call.enqueue(object : Callback<Login>{
            override fun onResponse(call: Call<Login>?, response: Response<Login>?) {
                if (response!!.isSuccessful && response.body()!!.isResponseOk)
                {
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                    saveToken(activity, response.body()!!.token)
                    activity.startActivity(Intent(activity, MainActivity::class.java))
                    activity.finish()
                }
                else if (!response.body()!!.isResponseOk)
                {
                    signinDialog.dismiss()
                    Toast.makeText(activity, response.body()!!.token, Toast.LENGTH_SHORT).show()
                }
                else
                {
                    signinDialog.dismiss()
                    Toast.makeText(activity, "Something weird happend", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Login>?, t: Throwable?) {
                if (t is SocketTimeoutException)
                {
                    Toast.makeText(activity, "Time out", Toast.LENGTH_SHORT).show()
                }            }
        })


    }

    private fun saveToken(context: Context, token:String) {
        val sharedpreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE) ?: return
        with(sharedpreferences.edit()){
            putString("token",token)
            apply()
        }
        /*val editor = sharedpreferences.edit()
        editor.putString("token", token)
        editor.apply()*/
    }
/*    private fun startMain(activity:Activity) {
        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
    }*/
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