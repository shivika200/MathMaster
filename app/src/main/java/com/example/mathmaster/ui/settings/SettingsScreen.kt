
package com.example.mathmaster.ui.settings

import android.Manifest
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mathmaster.notifications.ReminderScheduler
import com.example.mathmaster.viewmodel.MathMasterViewModelFactory
import com.example.mathmaster.viewmodel.SettingsViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    factory: MathMasterViewModelFactory,
    onBack: () -> Unit
) {

    val settingsViewModel: SettingsViewModel = viewModel(
        factory = factory
    )

    val settings by settingsViewModel.settings.collectAsState()
    val context = LocalContext.current

    // Requesting notification permission when reminders are enabled
    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {

                settingsViewModel.updateReminders(true)

                ReminderScheduler.scheduleReminder(
                    context = context,
                    hour = settings.reminderHour,
                    minute = settings.reminderMinute
                )

            } else {
                settingsViewModel.updateReminders(false)
            }
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Settings")
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
                text = "Personalise Your Learning",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Adjust MathMaster to suit your learning preferences.",
                style = MaterialTheme.typography.bodyMedium
            )

            SettingsCard(title = "Learning Difficulty") {

                Text("Choose your preferred question difficulty.")

                listOf(
                    "Beginner",
                    "Intermediate",
                    "Advanced"
                ).forEach { difficulty ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = settings.difficulty == difficulty,
                            onClick = {
                                settingsViewModel.updateDifficulty(difficulty)
                            }
                        )

                        Text(
                            text = difficulty,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            SettingsCard(title = "Sound") {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Enable sound effects")

                        Text(
                            text = "Control learning sound effects.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Switch(
                        checked = settings.soundEnabled,
                        onCheckedChange = {
                            settingsViewModel.updateSound(it)
                        }
                    )
                }
            }

            SettingsCard(title = "Learning Reminders") {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Enable reminders")

                        Text(
                            text = "Choose whether to receive learning reminders.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Switch(
                        checked = settings.remindersEnabled,
                        onCheckedChange = { enabled ->

                            if (!enabled) {

                                settingsViewModel.updateReminders(false)

                                ReminderScheduler.cancelReminder(context)

                            } else {

                                if (
                                    Build.VERSION.SDK_INT >=
                                    Build.VERSION_CODES.TIRAMISU
                                ) {

                                    val permissionGranted =
                                        ContextCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.POST_NOTIFICATIONS
                                        ) == PackageManager.PERMISSION_GRANTED

                                    if (permissionGranted) {

                                        settingsViewModel.updateReminders(true)

                                        ReminderScheduler.scheduleReminder(
                                            context,
                                            settings.reminderHour,
                                            settings.reminderMinute
                                        )

                                    } else {

                                        notificationPermissionLauncher.launch(
                                            Manifest.permission.POST_NOTIFICATIONS
                                        )
                                    }

                                } else {

                                    settingsViewModel.updateReminders(true)

                                    ReminderScheduler.scheduleReminder(
                                        context,
                                        settings.reminderHour,
                                        settings.reminderMinute
                                    )
                                }
                            }
                        }
                    )
                }

                if (settings.remindersEnabled) {

                    Spacer(modifier = Modifier.height(8.dp))

                    val formattedTime = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        settings.reminderHour,
                        settings.reminderMinute
                    )

                    Text(
                        text = "Reminder time: $formattedTime",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Button(
                        onClick = {

                            TimePickerDialog(
                                context,
                                { _, hour, minute ->

                                    settingsViewModel.updateReminderTime(
                                        hour,
                                        minute
                                    )

                                    ReminderScheduler.scheduleReminder(
                                        context,
                                        hour,
                                        minute
                                    )
                                },
                                settings.reminderHour,
                                settings.reminderMinute,
                                false
                            ).show()
                        }
                    ) {
                        Text("Choose Reminder Time")
                    }
                }
            }

            SettingsCard(title = "Accessibility") {

                Text("Choose the text size used in the application.")

                listOf(
                    "Small",
                    "Medium",
                    "Large"
                ).forEach { size ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected = settings.textSize == size,
                            onClick = {
                                settingsViewModel.updateTextSize(size)
                            }
                        )

                        Text(
                            text = size,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Text(
                text = "Your preferences are saved locally on this device.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun SettingsCard(
    title: String,
    content: @Composable () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Divider()

            content()
        }
    }
}
