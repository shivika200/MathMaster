
package com.example.mathmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathmaster.data.repository.MathRepository
import com.example.mathmaster.model.LearningProgress
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class StatisticsViewModel(
    repository: MathRepository
) : ViewModel() {

    // Combining the saved learning statistics
    val progress: StateFlow<LearningProgress> = combine(
        repository.getTotalAttempts(),
        repository.getCorrectAnswers(),
        repository.getIncorrectAnswers(),
        repository.getCompletedQuestions()
    ) { total, correct, incorrect, completed ->

        val accuracy = if (total > 0) {
            (correct.toFloat() / total.toFloat()) * 100f
        } else {
            0f
        }

        LearningProgress(
            totalAttempts = total,
            correctAnswers = correct,
            incorrectAnswers = incorrect,
            accuracy = accuracy,
            completedActivities = completed
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LearningProgress()
    )
}
