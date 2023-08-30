package com.example.objectdetection

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val name: String,
    val icon: ImageVector
) {

    object Home: BottomBarScreen(
        route = "home",
        name = "Home",
        icon = Icons.Filled.Home
    )

    object Scan: BottomBarScreen(
        route = "scan",
        name = "Scan",
        icon = Icons.Filled.Camera
    )

    object Setting: BottomBarScreen(
        route = "setting",
        name = "Setting",
        icon = Icons.Filled.Settings
    )
}