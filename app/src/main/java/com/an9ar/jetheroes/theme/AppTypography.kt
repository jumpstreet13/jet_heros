package com.an9ar.jetheroes.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.an9ar.jetheroes.R

data class AppTypography internal constructor(
        val paragraph1: TextStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.montserrat_light)),
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp
        ),
        val h1: TextStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.montserrat_light)),
                fontWeight = FontWeight.Light,
                fontSize = 96.sp,
                letterSpacing = (-1.5).sp
        ),
        val h2: TextStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.montserrat_light)),
                fontWeight = FontWeight.Light,
                fontSize = 60.sp,
                letterSpacing = (-0.5).sp
        ),
        val h3: TextStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.montserrat_light)),
                fontWeight = FontWeight.Normal,
                fontSize = 48.sp,
                letterSpacing = 0.sp
        ),
        val h4: TextStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.montserrat_light)),
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                letterSpacing = 0.sp
        ),
        val h5: TextStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.montserrat_light)),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                letterSpacing = 0.sp
        ),
        val h6: TextStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.montserrat_light)),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                letterSpacing = 0.sp
        ),
        val bookItemTitle: TextStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.montserrat_light)),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
                letterSpacing = 0.sp,
                lineHeight = 24.sp
        ),
        val bookItemAuthor: TextStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.montserrat_light)),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                letterSpacing = 0.sp,
                lineHeight = 24.sp
        ),
        val body1: TextStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.montserrat_light)),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                letterSpacing = 0.sp,
                lineHeight = 24.sp
        ),
        val body2: TextStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.montserrat_light)),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                letterSpacing = 0.25.sp
        ),
        val button: TextStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.montserrat_light)),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                letterSpacing = 1.25.sp
        ),
        val materialTypography: Typography = Typography(
                body1 = paragraph1
        )
)