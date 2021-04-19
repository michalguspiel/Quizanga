package com.erdees.quizanga.repository

import com.erdees.quizanga.models.Question
import com.erdees.quizanga.models.Questions
import com.erdees.quizanga.network.RetrofitClientInstance
import retrofit2.Call

class QuestionRepository {

    fun getQuestion(): Call<Questions> {
       return RetrofitClientInstance.api.getQuestion()
    }
}