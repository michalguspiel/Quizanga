package com.erdees.quizanga.network

import com.erdees.quizanga.gameLogic.levelOfDifficult.LevelOfDifficult
import com.erdees.quizanga.models.Question
import com.erdees.quizanga.models.Questions
import retrofit2.Call
import retrofit2.http.GET


interface GetDataService {
    @GET("api.php?amount=10&difficulty=easy")
    fun getEasyQuestion(): Call<Questions>

    @GET("api.php?amount=10&difficulty=hard")
    fun getHardQuestion(): Call<Questions>

    @GET("api.php?amount=10&difficulty=medium")
    fun getMediumQuestion(): Call<Questions>
}