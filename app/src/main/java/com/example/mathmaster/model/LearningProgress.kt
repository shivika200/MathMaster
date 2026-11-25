
package com.example.mathmaster.model

data class LearningProgress(
    val totalAttempts: Int = 0,
    val correctAnswers: Int = 0,
    val incorrectAnswers: Int = 0,
    val accuracy: Float = 0f,
    val completedActivities: Int = 0
)
