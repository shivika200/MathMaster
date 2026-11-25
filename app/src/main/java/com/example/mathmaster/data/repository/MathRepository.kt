
package com.example.mathmaster.data.repository

import android.text.Html
import com.example.mathmaster.data.local.MathMasterDatabase
import com.example.mathmaster.data.local.QuestionEntity
import com.example.mathmaster.data.local.ProgressEntity
import com.example.mathmaster.data.remote.MathApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow

class MathRepository(
    private val database: MathMasterDatabase,
    private val apiService: MathApiService
) {

    private val questionDao = database.questionDao()
    private val progressDao = database.progressDao()
    private val gson = Gson()

    fun getAllQuestions(): Flow<List<QuestionEntity>> {
        return questionDao.getAllQuestions()
    }

    fun getQuestionsByCategory(
        category: String
    ): Flow<List<QuestionEntity>> {
        return questionDao.getQuestionsByCategory(category)
    }

    fun getAllAttempts(): Flow<List<ProgressEntity>> {
        return progressDao.getAllAttempts()
    }

    fun getTotalAttempts(): Flow<Int> {
        return progressDao.getTotalAttempts()
    }

    fun getCorrectAnswers(): Flow<Int> {
        return progressDao.getCorrectAnswers()
    }

    fun getIncorrectAnswers(): Flow<Int> {
        return progressDao.getIncorrectAnswers()
    }

    fun getCompletedQuestions(): Flow<Int> {
        return progressDao.getCompletedQuestions()
    }

    suspend fun saveQuestion(question: QuestionEntity) {
        questionDao.insertQuestion(question)
    }

    suspend fun refreshQuestions(): Result<Int> {
        return try {
            val response = apiService.getMathematicsQuestions()

            if (response.responseCode != 0) {
                return Result.failure(
                    Exception(
                        "The question service returned code ${response.responseCode}"
                    )
                )
            }

            val questions = response.results.map { item ->

                val questionText = decodeHtml(item.question)
                val correctAnswer = decodeHtml(item.correctAnswer)

                val incorrectAnswers =
                    item.incorrectAnswers.map { decodeHtml(it) }

                val allOptions =
                    (incorrectAnswers + correctAnswer).shuffled()

                QuestionEntity(
                    category = "Quiz",
                    question = questionText,
                    correctAnswer = correctAnswer,
                    explanation = "The correct answer is $correctAnswer.",
                    difficulty = item.difficulty.replaceFirstChar {
                        it.uppercase()
                    },
                    options = gson.toJson(allOptions)
                )
            }

            if (questions.isNotEmpty()) {
                questionDao.insertQuestions(questions)
            }

            Result.success(questions.size)

        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    suspend fun ensureLocalQuestions() {

        // Checking whether local questions are already available
        if (questionDao.getQuestionCount() > 0) {
            return
        }

        // Creating the initial mathematics question collection
        val questions = listOf(

            createQuestion(
                category = "Multiplication",
                question = "What is 7 × 8?",
                answer = "56",
                explanation = "7 groups of 8 make 56.",
                options = listOf("48", "54", "56", "64")
            ),

            createQuestion(
                category = "Multiplication",
                question = "What is 6 × 9?",
                answer = "54",
                explanation = "6 groups of 9 make 54.",
                options = listOf("45", "54", "56", "63")
            ),

            createQuestion(
                category = "Addition",
                question = "What is 25 + 15?",
                answer = "40",
                explanation = "25 plus 15 equals 40.",
                options = listOf("30", "35", "40", "45")
            ),

            createQuestion(
                category = "Subtraction",
                question = "What is 50 - 18?",
                answer = "32",
                explanation = "50 minus 18 equals 32.",
                options = listOf("28", "30", "32", "34")
            ),

            createQuestion(
                category = "Division",
                question = "What is 36 ÷ 6?",
                answer = "6",
                explanation = "36 divided into 6 equal groups gives 6.",
                options = listOf("4", "5", "6", "8")
            ),

            createQuestion(
                category = "Puzzles",
                question = "Complete the sequence: 5, 10, 15, 20, ?",
                answer = "25",
                explanation = "The sequence increases by 5 each time.",
                options = listOf("22", "25", "30", "35")
            ),

            createQuestion(
                category = "Puzzles",
                question = "What number is double 12?",
                answer = "24",
                explanation = "Double 12 means 12 multiplied by 2.",
                options = listOf("18", "20", "24", "26")
            ),

            // Adding the first Quiz question
            createQuestion(
                category = "Quiz",
                question = "What is 8 × 7?",
                answer = "56",
                explanation = "8 multiplied by 7 equals 56.",
                options = listOf("48", "54", "56", "64")
            ),

            // Adding the second Quiz question
            createQuestion(
                category = "Quiz",
                question = "What is 100 - 35?",
                answer = "65",
                explanation = "100 minus 35 equals 65.",
                options = listOf("55", "60", "65", "75")
            )
        )

        // Saving the initial questions into the Room database
        questionDao.insertQuestions(questions)
    }

    suspend fun recordAttempt(
        category: String,
        question: String,
        selectedAnswer: String,
        correctAnswer: String,
        isCorrect: Boolean
    ) {
        progressDao.insertAttempt(
            ProgressEntity(
                category = category,
                question = question,
                selectedAnswer = selectedAnswer,
                correctAnswer = correctAnswer,
                isCorrect = isCorrect
            )
        )
    }

    suspend fun clearLearningProgress() {
        progressDao.clearProgress()
    }

    private fun createQuestion(
        category: String,
        question: String,
        answer: String,
        explanation: String,
        options: List<String>
    ): QuestionEntity {

        return QuestionEntity(
            category = category,
            question = question,
            correctAnswer = answer,
            explanation = explanation,
            difficulty = "Beginner",
            options = gson.toJson(options)
        )
    }

    private fun decodeHtml(value: String): String {
        return Html.fromHtml(
            value,
            Html.FROM_HTML_MODE_LEGACY
        ).toString()
    }

    fun decodeOptions(options: String): List<String> {

        val type = object : TypeToken<List<String>>() {}.type

        return try {
            gson.fromJson<List<String>>(options, type) ?: emptyList()
        } catch (exception: Exception) {
            emptyList()
        }
    }
}
