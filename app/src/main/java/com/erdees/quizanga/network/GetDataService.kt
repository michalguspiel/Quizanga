package com.erdees.quizanga.network

import android.provider.Settings
import com.erdees.quizanga.models.Question
import retrofit2.Call
import retrofit2.http.GET


interface GetDataService {
    @GET("api.php?amount=1")
    fun getQuestion(): Call<Question>
}