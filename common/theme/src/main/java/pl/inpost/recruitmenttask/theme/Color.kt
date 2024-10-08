package pl.inpost.recruitmenttask.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val ShipGray = Color(0xFF404041)
val OsloGray = Color(0xFF929497)
val SilverSand = Color(0xFFBBBDBF)
val Mercury = Color(0xFFE9E9E9)
val Iron = Color(0xFFE5E6E7)
val Concrete = Color(0xFFF2F2F2)
val White = Color(0xFFFFFFFF)

data class AppColors(
    val shadowStart: Color = Iron,
    val shadowEnd: Color = Concrete,
    val listHeaderLine: Color = Mercury,
)