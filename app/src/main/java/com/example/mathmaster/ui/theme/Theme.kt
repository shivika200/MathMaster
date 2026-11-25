
package com.example.mathmaster.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MathMasterColors = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF3867D6),
    onPrimary = androidx.compose.ui.graphics.Color.White,

    secondary = androidx.compose.ui.graphics.Color(0xFF16A085),
    onSecondary = androidx.compose.ui.graphics.Color.White,

    tertiary = androidx.compose.ui.graphics.Color(0xFFFFB142),

    background = androidx.compose.ui.graphics.Color(0xFFF5F7FB),
    surface = androidx.compose.ui.graphics.Color.White,

    error = androidx.compose.ui.graphics.Color(0xFFD63031)
)

@Composable
fun MathMasterTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MathMasterColors,
        content = content
    )
}
