
package com.example.mathmaster.notifications

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class LearningReminderWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {

        return try {
            NotificationHelper.showLearningReminder(applicationContext)
            Result.success()
        } catch (exception: Exception) {
            Result.retry()
        }
    }
}
