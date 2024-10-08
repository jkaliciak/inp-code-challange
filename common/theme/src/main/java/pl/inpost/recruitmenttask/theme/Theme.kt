package pl.inpost.recruitmenttask.theme

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
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
)

private val LightColorScheme = lightColorScheme(
    primary = ShipGray,
    onPrimary = White,
    secondary = ShipGray,
    onSecondary = White,
    tertiary = ShipGray,
    onTertiary = White,
    background = Concrete,
    onBackground = OsloGray,
    surface = White,
    onSurface = ShipGray,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTheme.typography.materialTypography,
        content = content,
    )
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = CompositionLocalAppColors.current
    val dimensions: AppDimensions
        @Composable
        get() = CompositionLocalAppDimensions.current
    val typography: AppTypography
        @Composable
        get() = CompositionLocalAppTypography.current
}

private val CompositionLocalAppColors = staticCompositionLocalOf { AppColors() }
private val CompositionLocalAppDimensions = staticCompositionLocalOf { AppDimensions() }
private val CompositionLocalAppTypography = staticCompositionLocalOf { AppTypography() }
