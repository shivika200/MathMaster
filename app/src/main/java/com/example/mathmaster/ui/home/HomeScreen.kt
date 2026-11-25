
package com.example.mathmaster.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.mathmaster.viewmodel.MathViewModel
import com.example.mathmaster.viewmodel.StatisticsViewModel

@Composable
fun HomeScreen(
    mathViewModel: MathViewModel,
    statisticsViewModel: StatisticsViewModel,
    onActivityClick: (String) -> Unit,
    onStatisticsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {

    val questions by mathViewModel.questions.collectAsState()

    val progress by statisticsViewModel.progress.collectAsState()

    LaunchedEffect(Unit) {
        mathViewModel.initialiseQuestions()
    }

    Scaffold(
        containerColor = Color(0xFFF5F7FB)
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {
                        Text(
                            text = "MATHMASTER",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF3867D6)
                        )

                        Text(
                            text = "Learn Mathematics Through Fun!",
                            fontSize = 13.sp,
                            color = Color(0xFF64748B)
                        )
                    }

                    OutlinedButton(
                        onClick = onSettingsClick,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Open settings"
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF3867D6)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(22.dp)
                    ) {

                        Text(
                            text = "Hello, Young Mathematician!",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Explore numbers, solve puzzles and " +
                                    "become a mathematics champion.",
                            fontSize = 14.sp,
                            color = Color.White,
                            lineHeight = 21.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Button(
                            onClick = {
                                onActivityClick("Multiplication")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFF3867D6)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Start Learning",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Your Learning Progress",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    ProgressCard(
                        title = "Attempts",
                        value = progress.totalAttempts.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    ProgressCard(
                        title = "Correct",
                        value = progress.correctAnswers.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    ProgressCard(
                        title = "Accuracy",
                        value = "${progress.accuracy.toInt()}%",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = onStatisticsClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.ShowChart,
                        contentDescription = null
                    )

                    Text(
                        text = "View Full Statistics",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            item {
                Text(
                    text = "Choose Your Activity",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            }

            item {
                ActivityCard(
                    title = "Multiplication Challenge",
                    description = "Practise multiplication tables and improve calculation skills.",
                    icon = Icons.Default.Calculate,
                    iconColor = Color(0xFF3867D6),
                    buttonText = "Start Challenge",
                    onClick = {
                        onActivityClick("Multiplication")
                    }
                )
            }

            item {
                ActivityCard(
                    title = "Mathematical Puzzles",
                    description = "Solve number patterns and missing-number problems.",
                    icon = Icons.Default.Extension,
                    iconColor = Color(0xFF16A085),
                    buttonText = "Solve Puzzles",
                    onClick = {
                        onActivityClick("Puzzles")
                    }
                )
            }

            item {
                ActivityCard(
                    title = "Mathematics Quiz",
                    description = "Test your knowledge through interactive questions.",
                    icon = Icons.Default.MenuBook,
                    iconColor = Color(0xFFE67E22),
                    buttonText = "Start Quiz",
                    onClick = {
                        onActivityClick("Quiz")
                    }
                )
            }

            item {
                Text(
                    text = "Question Library: ${questions.size} questions available",
                    color = Color(0xFF64748B),
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = {
                        mathViewModel.refreshQuestions {}
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null
                    )

                    Text(
                        text = "Refresh Online Questions",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun ProgressCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = value,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3867D6)
            )

            Text(
                text = title,
                fontSize = 11.sp,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ActivityCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    buttonText: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    modifier = Modifier.size(52.dp),
                    shape = RoundedCornerShape(15.dp),
                    color = iconColor.copy(alpha = 0.12f)
                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = iconColor,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 14.dp)
                ) {

                    Text(
                        text = title,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = description,
                        fontSize = 12.sp,
                        color = Color(0xFF64748B),
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = buttonText)
            }
        }
    }
}
