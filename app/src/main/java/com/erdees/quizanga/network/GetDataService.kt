package com.erdees.quizanga.network

import com.erdees.quizanga.models.Question
import com.erdees.quizanga.models.Questions
import retrofit2.Call
import retrofit2.http.GET


interface GetDataService {
    @GET("api.php?amount=10")
    fun getQuestion(): Call<Questions>
}