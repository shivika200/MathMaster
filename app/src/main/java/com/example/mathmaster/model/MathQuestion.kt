
package com.example.mathmaster.model

data class MathQuestion(
    val id: Int = 0,
    val category: String,
    val question: String,
    val correctAnswer: String,
    val explanation: String,
    val difficulty: String = "Beginner"
)
