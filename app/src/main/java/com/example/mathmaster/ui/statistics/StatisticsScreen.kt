
package com.example.mathmaster.ui.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mathmaster.viewmodel.MathMasterViewModelFactory
import com.example.mathmaster.viewmodel.StatisticsViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    factory: MathMasterViewModelFactory,
    onBack: () -> Unit
) {
    val statisticsViewModel: StatisticsViewModel = viewModel(
        factory = factory
    )

    val progress by statisticsViewModel.progress.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My Learning Statistics")
                },
                navigationIcon = {
                    Button(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Your Learning Progress",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Review your mathematics practice and see how you are progressing.",
                style = MaterialTheme.typography.bodyMedium
            )

            // Displaying the total number of attempts
            StatisticCard(
                title = "Total Attempts",
                value = progress.totalAttempts.toString(),
                description = "Answers submitted"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatisticCard(
                    title = "Correct Answers",
                    value = progress.correctAnswers.toString(),
                    description = "Correct responses",
                    modifier = Modifier.weight(1f)
                )

                StatisticCard(
                    title = "Incorrect Answers",
                    value = progress.incorrectAnswers.toString(),
                    description = "Responses to review",
                    modifier = Modifier.weight(1f)
                )
            }

            // Displaying the learner's accuracy
            StatisticCard(
                title = "Accuracy",
                value = String.format(
                    Locale.getDefault(),
                    "%.1f%%",
                    progress.accuracy
                ),
                description = "Percentage of correct answers"
            )

            // Displaying unique questions attempted
            StatisticCard(
                title = "Unique Questions Attempted",
                value = progress.completedActivities.toString(),
                description = "Different questions answered"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Keep Learning!",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = if (progress.totalAttempts == 0) {
                            "Start a mathematics activity to begin tracking your learning progress."
                        } else {
                            "You have answered ${progress.totalAttempts} questions. Keep practising to improve your mathematics skills."
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun StatisticCard(
    title: String,
    value: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
