package com.openledger.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// 主色调 - 清新蓝绿
val Primary = Color(0xFF0F6E56)
val PrimaryLight = Color(0xFF5DCAA5)
val PrimaryContainer = Color(0xFFE1F5EE)
val OnPrimaryContainer = Color(0xFF04342C)

// 辅助色 - 温暖琥珀
val Secondary = Color(0xFFBA7517)
val SecondaryLight = Color(0xFFFAC775)
val SecondaryContainer = Color(0xFFFAEEDA)

// 功能色
val IncomeGreen = Color(0xFF3B6D11)
val IncomeBg = Color(0xFFEAF3DE)
val ExpenseRed = Color(0xFFA32D2D)
val ExpenseBg = Color(0xFFFCEBEB)

// 中性色
val SurfaceLight = Color(0xFFF8F7F4)
val SurfaceDark = Color(0xFF1C1C1C)
val TextPrimary = Color(0xFF2C2C2A)
val TextSecondary = Color(0xFF5F5E5A)
val TextHint = Color(0xFF888780)
val BorderLight = Color(0xFFE8E6DF)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = Color.White,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = Color(0xFF412402),
    tertiary = Color(0xFF534AB7),
    onTertiary = Color.White,
    background = Color(0xFFFCFBF9),
    onBackground = TextPrimary,
    surface = Color.White,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceLight,
    onSurfaceVariant = TextSecondary,
    error = ExpenseRed,
    onError = Color.White,
    outline = BorderLight,
    outlineVariant = Color(0xFFE8E6DF)
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryLight,
    onPrimary = Color(0xFF04342C),
    primaryContainer = Color(0xFF085041),
    onPrimaryContainer = Color(0xFF9FE1CB),
    secondary = SecondaryLight,
    onSecondary = Color(0xFF412402),
    secondaryContainer = Color(0xFF633806),
    onSecondaryContainer = Color(0xFFFAC775),
    background = Color(0xFF141413),
    onBackground = Color(0xFFE8E6DF),
    surface = SurfaceDark,
    onSurface = Color(0xFFE8E6DF),
    surfaceVariant = Color(0xFF2A2A28),
    onSurfaceVariant = Color(0xFFB4B2A9),
    error = Color(0xFFF09595),
    onError = Color(0xFF501313),
    outline = Color(0xFF444441),
    outlineVariant = Color(0xFF2C2C2A)
)

private val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp
    )
)

@Composable
fun OpenLedgerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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
        typography = AppTypography,
        content = content
    )
}