
package com.example.mathmaster.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val category: String,

    val question: String,

    val correctAnswer: String,

    val explanation: String,

    val difficulty: String = "Beginner",

    val options: String = "[]"
)
