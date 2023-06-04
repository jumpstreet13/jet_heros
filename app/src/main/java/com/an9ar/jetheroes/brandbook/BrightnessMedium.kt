package com.an9ar.jetheroes.brandbook

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val Icons.Rounded.BrightnessMedium: ImageVector
    get() {
        if (_brightnessMedium != null) {
            return _brightnessMedium!!
        }
        _brightnessMedium = materialIcon(name = "Rounded.BrightnessMedium") {
            materialPath {
                moveTo(20.0f, 15.31f)
                lineToRelative(1.9f, -1.9f)
                curveToRelative(0.78f, -0.78f, 0.78f, -2.05f, 0.0f, -2.83f)
                lineTo(20.0f, 8.69f)
                lineTo(20.0f, 6.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineToRelative(-2.69f)
                lineToRelative(-1.9f, -1.9f)
                curveToRelative(-0.78f, -0.78f, -2.05f, -0.78f, -2.83f, 0.0f)
                lineTo(8.69f, 4.0f)
                lineTo(6.0f, 4.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(2.69f)
                lineToRelative(-1.9f, 1.9f)
                curveToRelative(-0.78f, 0.78f, -0.78f, 2.05f, 0.0f, 2.83f)
                lineToRelative(1.9f, 1.9f)
                lineTo(4.0f, 18.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(2.69f)
                lineToRelative(1.9f, 1.9f)
                curveToRelative(0.78f, 0.78f, 2.05f, 0.78f, 2.83f, 0.0f)
                lineToRelative(1.9f, -1.9f)
                lineTo(18.0f, 20.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineToRelative(-2.69f)
                close()
                moveTo(12.0f, 16.9f)
                lineTo(12.0f, 7.1f)
                curveToRelative(0.0f, -0.61f, 0.55f, -1.11f, 1.15f, -0.99f)
                curveTo(15.91f, 6.65f, 18.0f, 9.08f, 18.0f, 12.0f)
                reflectiveCurveToRelative(-2.09f, 5.35f, -4.85f, 5.89f)
                curveToRelative(-0.6f, 0.12f, -1.15f, -0.38f, -1.15f, -0.99f)
                close()
            }
        }
        return _brightnessMedium!!
    }

private var _brightnessMedium: ImageVector? = null