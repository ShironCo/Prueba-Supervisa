package com.example.pruebatecnicasupervisa.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Blue80Dark,
    onSurface = Blue80Dark,
    surface = BlackDark,
    onSurfaceVariant = Blue30Dark,
    surfaceBright = Blue30Dark,
    surfaceContainerHigh = BlackDark,
    onSecondary = WhiteDark,
    outline = GreyDark
)

private val LightColorScheme = lightColorScheme(
    primary = Blue80,
    onSurface = Blue80,
    surface = White,
    onSurfaceVariant = Blue30,
    surfaceBright = Blue30,
    surfaceContainerHigh = White,
    onSecondary = Black,
    outline = Grey
)

@Composable
fun PruebaTecnicaSupervisaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}