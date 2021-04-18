package com.erdees.quizanga.network

import android.provider.Settings.Secure.ANDROID_ID
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClientInstance {

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

     val api : GetDataService by lazy {
        retrofit.create(GetDataService::class.java)
    }


    private const val BASE_URL = "https://opentdb.com/"

}