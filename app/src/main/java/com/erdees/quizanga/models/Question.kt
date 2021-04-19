package com.erdees.quizanga.models

import com.google.gson.annotations.SerializedName

data class Question(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: ArrayList<String>
)
{
}

class Questions(
    var results: List<Question>,
    val response_code: Int
)