
package com.example.mathmaster.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mathmaster.data.repository.MathRepository

class MathMasterViewModelFactory(
    private val application: Application,
    private val repository: MathRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        return when {

            modelClass.isAssignableFrom(
                MathViewModel::class.java
            ) -> {
                MathViewModel(repository) as T
            }

            modelClass.isAssignableFrom(
                QuizViewModel::class.java
            ) -> {
                QuizViewModel(repository) as T
            }

            modelClass.isAssignableFrom(
                StatisticsViewModel::class.java
            ) -> {
                StatisticsViewModel(repository) as T
            }

            modelClass.isAssignableFrom(
                SettingsViewModel::class.java
            ) -> {
                SettingsViewModel(application) as T
            }

            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}"
            )
        }
    }
}
