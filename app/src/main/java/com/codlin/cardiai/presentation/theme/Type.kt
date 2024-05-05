package com.codlin.cardiai.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.codlin.cardiai.R

val OpenSansFontFamily = FontFamily(
    Font(R.font.opensans_bold, weight = FontWeight.W700),
    Font(R.font.opensans_semibold, weight = FontWeight.W600),
    Font(R.font.opensans_regular, weight = FontWeight.W400),
    Font(R.font.opensans_medium, weight = FontWeight.W500),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontSize = 18.sp,
        lineHeight = 25.2.sp,
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.W600,
    ),
    displaySmall = TextStyle(
        fontSize = 32.sp,
        lineHeight = 38.4.sp,
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.W700,
        color = Color(0xFF000000),
    ),
    headlineSmall = TextStyle(
        fontSize = 20.sp,
        lineHeight = 24.sp,
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.W400,
        color = Color(0xFF000000),
    ),
    headlineMedium = TextStyle(
        fontSize = 24.sp,
        lineHeight = 28.8.sp,
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.W700,
    ),
    // Content Bold
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 16.8.sp,
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.W700
    ),
    // Content Regular
    bodySmall = TextStyle(
        fontSize = 14.sp,
        lineHeight = 16.8.sp,
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.W400,
    ),
    // Footnote Regular
    labelSmall = TextStyle(
        fontSize = 14.sp,
        lineHeight = 12.sp,
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.W400,
    ),
    //Label Large
    labelLarge = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.1.sp,
    ),
    //Caption Regular
    displayMedium = TextStyle(
        fontSize = 12.sp,
        lineHeight = 14.4.sp,
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.W400,
    )
)