package com.erdees.quizanga.repository

import com.erdees.quizanga.gameLogic.levelOfDifficult.Easy
import com.erdees.quizanga.gameLogic.levelOfDifficult.LevelOfDifficult
import com.erdees.quizanga.gameLogic.levelOfDifficult.Medium
import com.erdees.quizanga.models.Question
import com.erdees.quizanga.models.Questions
import com.erdees.quizanga.network.RetrofitClientInstance
import retrofit2.Call

class QuestionRepository {

    fun getQuestion(difficult: LevelOfDifficult): Call<Questions> {
        return when(difficult){
            Easy -> RetrofitClientInstance.api.getEasyQuestion()
            Medium -> RetrofitClientInstance.api.getMediumQuestion()
                else -> RetrofitClientInstance.api.getHardQuestion()
        }
    }
}