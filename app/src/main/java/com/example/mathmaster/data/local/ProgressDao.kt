
package com.example.mathmaster.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {

    @Insert
    suspend fun insertAttempt(
        attempt: ProgressEntity
    )

    @Query("SELECT * FROM learning_progress ORDER BY timestamp DESC")
    fun getAllAttempts(): Flow<List<ProgressEntity>>

    @Query("SELECT * FROM learning_progress WHERE category = :category ORDER BY timestamp DESC")
    fun getAttemptsByCategory(
        category: String
    ): Flow<List<ProgressEntity>>

    @Query("SELECT COUNT(*) FROM learning_progress")
    fun getTotalAttempts(): Flow<Int>

    @Query("SELECT COUNT(*) FROM learning_progress WHERE isCorrect = 1")
    fun getCorrectAnswers(): Flow<Int>

    @Query("SELECT COUNT(*) FROM learning_progress WHERE isCorrect = 0")
    fun getIncorrectAnswers(): Flow<Int>

    @Query("SELECT COUNT(DISTINCT question) FROM learning_progress")
    fun getCompletedQuestions(): Flow<Int>

    @Query("DELETE FROM learning_progress")
    suspend fun clearProgress()
}