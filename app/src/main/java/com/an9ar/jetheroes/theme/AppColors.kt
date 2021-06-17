package com.an9ar.jetheroes.theme

import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object AppColors {
    val primary = Color(0xFF3366FF)
    val background = Color(0xFFE7E7E7)
    val backgroundReverse = Color(0xFF141414)
    val toolbar = Color(0xFFE7E7E7)
    val toolbarReverse = Color(0xFF1C1C1C)
    val card = Color(0xFFFFFFFF)
    val cardReverse = Color(0xFF252525)
    val text = Color(0xFF111111)
    val textAccent = Color(0xFF00BCD4)
    val textSecondary = Color(0xFF333333)
    val textReverse = Color(0xFFEEEEEE)
    val textReverseSecondary = Color(0xFFCCCCCC)
    val success = Color(0xFF00E096)
    val warning = Color(0xFFFFAA00)
    val error = Color(0xFFFF3D31)
    val light = Color(0xFFCCCCCC)
    val dark = Color(0xFF1E1E1E)
    val transparent = Color(0x00000000)
}

interface ColorPalette {
    val primary: Color
    val background: Color
    val toolbar: Color
    val card: Color
    val text: Color
    val textAccent: Color
    val textSecondary: Color
    val uiSurface: Color
    val uiSurfaceInverted: Color
    val success: Color
    val warning: Color
    val error: Color
    val light: Color
    val dark: Color
    val transparent: Color

    val materialColors: Colors
}

fun lightColorPalette(): ColorPalette = object : ColorPalette {
    override val primary: Color = AppColors.primary
    override val background: Color = AppColors.background
    override val toolbar: Color = AppColors.toolbar
    override val card: Color = AppColors.card
    override val text: Color = AppColors.text
    override val textAccent: Color = AppColors.textAccent
    override val textSecondary: Color = AppColors.textSecondary
    override val uiSurface: Color = AppColors.dark
    override val uiSurfaceInverted: Color = AppColors.light
    override val success: Color = AppColors.success
    override val warning: Color = AppColors.warning
    override val error: Color = AppColors.error
    override val light: Color = AppColors.light
    override val dark: Color = AppColors.dark
    override val transparent: Color = AppColors.transparent

    override val materialColors: Colors = lightColors(
        primary = AppColors.primary,
        surface = AppColors.backgroundReverse,
        onSurface = AppColors.textReverse
    )
}

fun darkColorPalette(): ColorPalette = object : ColorPalette {
    override val primary: Color = AppColors.primary
    override val background: Color = AppColors.backgroundReverse
    override val toolbar: Color = AppColors.toolbarReverse
    override val card: Color = AppColors.cardReverse
    override val text: Color = AppColors.textReverse
    override val textAccent: Color = AppColors.textAccent
    override val textSecondary: Color = AppColors.textReverseSecondary
    override val uiSurface: Color = AppColors.light
    override val uiSurfaceInverted: Color = AppColors.dark
    override val success: Color = AppColors.success
    override val warning: Color = AppColors.warning
    override val error: Color = AppColors.error
    override val light: Color = AppColors.light
    override val dark: Color = AppColors.dark
    override val transparent: Color = AppColors.transparent

    override val materialColors: Colors = lightColors(
        primary = AppColors.primary,
        surface = AppColors.background,
        onSurface = AppColors.textReverse
    )
}