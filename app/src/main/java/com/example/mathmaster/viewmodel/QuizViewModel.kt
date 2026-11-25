
package com.example.mathmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathmaster.data.local.QuestionEntity
import com.example.mathmaster.data.repository.MathRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class QuizUiState(
    val questions: List<QuestionEntity> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: String? = null,
    val answerChecked: Boolean = false,
    val isCorrect: Boolean = false,
    val score: Int = 0,
    val completed: Boolean = false,
    val loading: Boolean = true,
    val feedback: String = "",
    val error: String? = null
)

class QuizViewModel(
    private val repository: MathRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())

    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private var currentCategory = "Multiplication"

    fun loadQuiz(category: String = "Multiplication") {

        currentCategory = category

        viewModelScope.launch {

            _uiState.value = QuizUiState(
                loading = true
            )

            try {

                val allQuestions =
                    repository.getAllQuestions().first()

                val categoryQuestions =
                    allQuestions.filter {
                        it.category.equals(category, ignoreCase = true)
                    }.shuffled()

                if (categoryQuestions.isEmpty()) {

                    _uiState.value = QuizUiState(
                        loading = false,
                        error = "No questions available. Please try another activity."
                    )

                    return@launch
                }

                _uiState.value = QuizUiState(
                    questions = categoryQuestions,
                    loading = false
                )

            } catch (exception: Exception) {

                _uiState.value = QuizUiState(
                    loading = false,
                    error = exception.message ?: "Unable to load questions."
                )
            }
        }
    }

    fun selectAnswer(answer: String) {

        val state = _uiState.value

        if (state.answerChecked || state.completed) return

        _uiState.value = state.copy(
            selectedAnswer = answer,
            feedback = ""
        )
    }

    fun checkAnswer() {

        val state = _uiState.value

        if (state.selectedAnswer == null ||
            state.answerChecked ||
            state.completed
        ) return

        val question = state.questions[state.currentIndex]

        val correct =
            state.selectedAnswer.trim().equals(
                question.correctAnswer.trim(),
                ignoreCase = true
            )

        val updatedScore =
            if (correct) state.score + 1 else state.score

        _uiState.value = state.copy(
            answerChecked = true,
            isCorrect = correct,
            score = updatedScore,
            feedback = if (correct) {
                "Excellent! ${question.explanation}"
            } else {
                "Not quite. ${question.explanation}"
            }
        )

        viewModelScope.launch {

            repository.recordAttempt(
                category = question.category,
                question = question.question,
                selectedAnswer = state.selectedAnswer,
                correctAnswer = question.correctAnswer,
                isCorrect = correct
            )
        }
    }

    fun nextQuestion() {

        val state = _uiState.value

        if (!state.answerChecked || state.completed) return

        val nextIndex = state.currentIndex + 1

        if (nextIndex >= state.questions.size) {

            _uiState.value = state.copy(
                completed = true,
                selectedAnswer = null
            )

        } else {

            _uiState.value = state.copy(
                currentIndex = nextIndex,
                selectedAnswer = null,
                answerChecked = false,
                isCorrect = false,
                feedback = ""
            )
        }
    }

    fun restartQuiz() {
        loadQuiz(currentCategory)
    }

    fun getOptions(question: QuestionEntity): List<String> {
        return repository.decodeOptions(question.options)
    }
}
