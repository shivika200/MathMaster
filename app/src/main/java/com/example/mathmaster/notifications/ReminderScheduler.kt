
package com.example.mathmaster.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object ReminderScheduler {

    private const val REMINDER_WORK_NAME = "mathmaster_daily_reminder"

    fun scheduleReminder(
        context: Context,
        hour: Int,
        minute: Int
    ) {

        val now = Calendar.getInstance()

        val reminderTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (!after(now)) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val initialDelay = reminderTime.timeInMillis - now.timeInMillis

        val reminderRequest =
            PeriodicWorkRequestBuilder<LearningReminderWorker>(
                24,
                TimeUnit.HOURS
            )
                .setInitialDelay(
                    initialDelay,
                    TimeUnit.MILLISECONDS
                )
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            REMINDER_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            reminderRequest
        )
    }

    fun cancelReminder(context: Context) {

        WorkManager.getInstance(context)
            .cancelUniqueWork(REMINDER_WORK_NAME)
    }
}
