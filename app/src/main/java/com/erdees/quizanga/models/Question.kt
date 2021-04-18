package com.erdees.quizanga.models

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("category") var category: String = "",
    @SerializedName("type") var type: String = "",
    @SerializedName("difficulty") var difficulty: String = "",
    @SerializedName("question") var question: String = "",
    @SerializedName("correct_answer") var correctAnswer: String = "",
    @SerializedName("incorrect_answers") var wrongAnswers: List<String> = listOf()
)
{

}