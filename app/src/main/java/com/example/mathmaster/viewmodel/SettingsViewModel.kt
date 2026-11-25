
package com.example.mathmaster.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MathSettings(
    val difficulty: String = "Beginner",
    val soundEnabled: Boolean = true,
    val remindersEnabled: Boolean = false,
    val reminderHour: Int = 17,
    val reminderMinute: Int = 0,
    val textSize: String = "Medium"
)

class SettingsViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Initialising local preferences storage
    private val preferences = application.getSharedPreferences(
        "mathmaster_settings",
        Application.MODE_PRIVATE
    )

    // Loading the saved application settings
    private val _settings = MutableStateFlow(loadSettings())

    val settings: StateFlow<MathSettings> =
        _settings.asStateFlow()

    // Loading the saved preferences
    private fun loadSettings(): MathSettings {

        return MathSettings(
            difficulty = preferences.getString(
                "difficulty",
                "Beginner"
            ) ?: "Beginner",

            soundEnabled = preferences.getBoolean(
                "sound_enabled",
                true
            ),

            remindersEnabled = preferences.getBoolean(
                "reminders_enabled",
                false
            ),

            reminderHour = preferences.getInt(
                "reminder_hour",
                17
            ),

            reminderMinute = preferences.getInt(
                "reminder_minute",
                0
            ),

            textSize = preferences.getString(
                "text_size",
                "Medium"
            ) ?: "Medium"
        )
    }

    // Saving the updated application preferences
    private fun saveSettings(settings: MathSettings) {

        preferences.edit()
            .putString("difficulty", settings.difficulty)
            .putBoolean("sound_enabled", settings.soundEnabled)
            .putBoolean("reminders_enabled", settings.remindersEnabled)
            .putInt("reminder_hour", settings.reminderHour)
            .putInt("reminder_minute", settings.reminderMinute)
            .putString("text_size", settings.textSize)
            .apply()

        _settings.value = settings
    }

    // Updating the selected learning difficulty
    fun updateDifficulty(value: String) {
        saveSettings(
            _settings.value.copy(difficulty = value)
        )
    }

    // Updating the sound preference
    fun updateSound(enabled: Boolean) {
        saveSettings(
            _settings.value.copy(soundEnabled = enabled)
        )
    }

    // Updating the learning reminder preference
    fun updateReminders(enabled: Boolean) {
        saveSettings(
            _settings.value.copy(remindersEnabled = enabled)
        )
    }

    // Updating the scheduled reminder time
    fun updateReminderTime(hour: Int, minute: Int) {

        saveSettings(
            _settings.value.copy(
                reminderHour = hour,
                reminderMinute = minute
            )
        )
    }

    // Updating the application text size
    fun updateTextSize(value: String) {
        saveSettings(
            _settings.value.copy(textSize = value)
        )
    }
}
