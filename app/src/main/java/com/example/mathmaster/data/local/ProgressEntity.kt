
package com.example.mathmaster.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "learning_progress")
data class ProgressEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val category: String,

    val question: String,

    val selectedAnswer: String,

    val correctAnswer: String,

    val isCorrect: Boolean,

    val timestamp: Long = System.currentTimeMillis()
)