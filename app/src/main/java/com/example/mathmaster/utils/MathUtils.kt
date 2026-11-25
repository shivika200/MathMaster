
package com.example.mathmaster.utils

import com.example.mathmaster.model.MathQuestion

object MathUtils {

    // Checking whether the submitted answer is correct
    fun isAnswerCorrect(
        selectedAnswer: String,
        correctAnswer: String
    ): Boolean {
        return selectedAnswer.trim().equals(
            correctAnswer.trim(),
            ignoreCase = true
        )
    }

    // Calculating the percentage of correct answers
    fun calculateAccuracy(
        correctAnswers: Int,
        totalAttempts: Int
    ): Float {

        if (totalAttempts <= 0) {
            return 0f
        }

        if (correctAnswers < 0 || correctAnswers > totalAttempts) {
            return 0f
        }

        return (correctAnswers.toFloat() / totalAttempts.toFloat()) * 100f
    }

    // Checking whether a mathematics question contains valid data
    fun isValidQuestion(question: MathQuestion): Boolean {

        return question.category.isNotBlank() &&
                question.question.isNotBlank() &&
                question.correctAnswer.isNotBlank() &&
                question.explanation.isNotBlank()
    }
}
