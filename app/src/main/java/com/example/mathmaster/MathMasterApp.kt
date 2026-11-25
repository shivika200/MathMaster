
package com.example.mathmaster

import android.app.Application
import com.example.mathmaster.di.AppContainer
import com.example.mathmaster.notifications.NotificationHelper

class MathMasterApp : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()

        // Initialising the application dependencies
        appContainer = AppContainer(this)

        // Creating the learning reminder notification channel
        NotificationHelper.createNotificationChannel(this)
    }
}
