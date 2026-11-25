
package com.example.mathmaster.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mathmaster.ui.activity.ActivityScreen
import com.example.mathmaster.ui.home.HomeScreen
import com.example.mathmaster.ui.settings.SettingsScreen
import com.example.mathmaster.ui.statistics.StatisticsScreen
import com.example.mathmaster.viewmodel.MathMasterViewModelFactory
import com.example.mathmaster.viewmodel.MathViewModel
import com.example.mathmaster.viewmodel.QuizViewModel
import com.example.mathmaster.viewmodel.StatisticsViewModel

object MathMasterRoutes {
    const val HOME = "home"
    const val ACTIVITY = "activity/{category}"
    const val SETTINGS = "settings"
    const val STATISTICS = "statistics"

    fun activity(category: String): String {
        return "activity/${Uri.encode(category)}"
    }
}

@Composable
fun AppNavigation(
    factory: MathMasterViewModelFactory
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MathMasterRoutes.HOME
    ) {

        // Displaying the Home Screen
        composable(MathMasterRoutes.HOME) {

            val mathViewModel: MathViewModel = viewModel(
                factory = factory
            )

            val statisticsViewModel: StatisticsViewModel = viewModel(
                factory = factory
            )

            HomeScreen(
                mathViewModel = mathViewModel,
                statisticsViewModel = statisticsViewModel,

                onActivityClick = { category ->
                    navController.navigate(
                        MathMasterRoutes.activity(category)
                    )
                },

                onStatisticsClick = {
                    navController.navigate(
                        MathMasterRoutes.STATISTICS
                    )
                },

                onSettingsClick = {
                    navController.navigate(
                        MathMasterRoutes.SETTINGS
                    )
                }
            )
        }

        // Displaying the selected Activity Screen
        composable(
            route = MathMasterRoutes.ACTIVITY,
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val category = Uri.decode(
                backStackEntry.arguments?.getString("category") ?: "Multiplication"
            )

            val quizViewModel: QuizViewModel = viewModel(
                factory = factory
            )

            ActivityScreen(
                category = category,
                quizViewModel = quizViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Displaying the Settings Screen
        composable(MathMasterRoutes.SETTINGS) {

            SettingsScreen(
                factory = factory,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Displaying the Statistics Screen
        composable(MathMasterRoutes.STATISTICS) {

            StatisticsScreen(
                factory = factory,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
