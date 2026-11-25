
package com.example.mathmaster.ui.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathmaster.data.local.QuestionEntity
import com.example.mathmaster.viewmodel.QuizViewModel

@Composable
fun ActivityScreen(
    category: String,
    quizViewModel: QuizViewModel,
    onBack: () -> Unit
) {

    val state by quizViewModel.uiState.collectAsState()

    LaunchedEffect(category) {
        quizViewModel.loadQuiz(category)
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text("Back")
                }

                Text(
                    text = category,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { padding ->

        when {

            state.loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Loading your questions...",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            state.error != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.error ?: "Unable to load questions.",
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            quizViewModel.restartQuiz()
                        }
                    ) {
                        Text("Try Again")
                    }
                }
            }

            state.completed -> {
                QuizResultScreen(
                    score = state.score,
                    total = state.questions.size,
                    onRestart = {
                        quizViewModel.restartQuiz()
                    },
                    onBack = onBack
                )
            }

            else -> {
                val question: QuestionEntity =
                    state.questions[state.currentIndex]

                val options = quizViewModel.getOptions(question)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = "Question ${state.currentIndex + 1} of ${state.questions.size}",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    LinearProgressIndicator(
                        progress = {
                            (state.currentIndex + 1).toFloat() /
                                    state.questions.size.toFloat()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = question.category,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = question.question,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                lineHeight = 34.sp
                            )
                        }
                    }

                    Text(
                        text = "Choose your answer:",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    options.forEach { option ->

                        val selected =
                            state.selectedAnswer == option

                        OutlinedButton(
                            onClick = {
                                quizViewModel.selectAnswer(option)
                            },
                            enabled = !state.answerChecked,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (selected) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    Color.Transparent
                                }
                            )
                        ) {
                            Text(
                                text = option,
                                modifier = Modifier.padding(vertical = 5.dp),
                                fontSize = 17.sp
                            )
                        }
                    }

                    if (state.answerChecked) {

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = if (state.isCorrect) {
                                    Color(0xFFE4F6E9)
                                } else {
                                    Color(0xFFFFE9E9)
                                }
                            )
                        ) {
                            Text(
                                text = state.feedback,
                                modifier = Modifier.padding(16.dp),
                                color = Color(0xFF263238),
                                fontSize = 15.sp,
                                lineHeight = 22.sp
                            )
                        }

                        Text(
                            text = "Current score: ${state.score}/${state.currentIndex + 1}",
                            fontWeight = FontWeight.Bold
                        )

                        Button(
                            onClick = {
                                quizViewModel.nextQuestion()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                if (state.currentIndex ==
                                    state.questions.lastIndex
                                ) {
                                    "Finish Activity"
                                } else {
                                    "Next Question"
                                }
                            )
                        }

                    } else {

                        Button(
                            onClick = {
                                quizViewModel.checkAnswer()
                            },
                            enabled = state.selectedAnswer != null,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Check Answer")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuizResultScreen(
    score: Int,
    total: Int,
    onRestart: () -> Unit,
    onBack: () -> Unit
) {

    val percentage = if (total > 0) {
        score * 100 / total
    } else {
        0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Activity Completed!",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "$score / $total",
            fontSize = 42.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "Your score",
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "$percentage% accuracy",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = when {
                percentage == 100 -> "Excellent work! You answered every question correctly."
                percentage >= 70 -> "Great effort! Keep practising."
                percentage >= 40 -> "Good try! Review the explanations and practise again."
                else -> "Keep learning. Every attempt helps you improve."
            },
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Try Again")
        }

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Return Home")
        }
    }
}
