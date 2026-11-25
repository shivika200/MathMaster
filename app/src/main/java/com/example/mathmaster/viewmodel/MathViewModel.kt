
package com.example.mathmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathmaster.data.local.QuestionEntity
import com.example.mathmaster.data.repository.MathRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MathViewModel(
    private val repository: MathRepository
) : ViewModel() {

    val questions: StateFlow<List<QuestionEntity>> =
        repository.getAllQuestions().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun initialiseQuestions() {
        viewModelScope.launch {
            repository.ensureLocalQuestions()
        }
    }

    fun refreshQuestions(
        onResult: (Result<Int>) -> Unit
    ) {
        viewModelScope.launch {
            val result = repository.refreshQuestions()
            onResult(result)
        }
    }

    fun getQuestionsByCategory(category: String) =
        repository.getQuestionsByCategory(category)
}
