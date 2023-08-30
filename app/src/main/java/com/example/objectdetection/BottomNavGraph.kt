package com.example.objectdetection

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.objectdetection.screens.HomeScreen
import com.example.objectdetection.screens.ScanScreen
import com.example.objectdetection.screens.SettingScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    context: Context = LocalContext.current
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(paddingValues);
        }
        composable(route = BottomBarScreen.Scan.route) {
            ScanScreen(paddingValues, context = context);
        }
        composable(route = BottomBarScreen.Setting.route) {
            SettingScreen(paddingValues);
        }
    }
}