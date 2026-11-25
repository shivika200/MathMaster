
package com.example.mathmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mathmaster.ui.navigation.AppNavigation
import com.example.mathmaster.ui.theme.MathMasterTheme
import com.example.mathmaster.viewmodel.MathMasterViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialising the application and ViewModel factory
        val mathMasterApplication = application as MathMasterApp

        val factory = MathMasterViewModelFactory(
            application = mathMasterApplication,
            repository = mathMasterApplication.appContainer.mathRepository
        )

        // Displaying the MathMaster application
        setContent {
            MathMasterTheme {
                AppNavigation(
                    factory = factory
                )
            }
        }
    }
}
