package com.example.objectdetection

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PestControl
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.objectdetection.ui.theme.ObjectDetectionTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            ObjectDetectionTheme {
                App(context = this)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(context: Context = LocalContext.current) {

    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = {
                    Text(
                        text = "PestGon",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.PestControl,
                        contentDescription = "logo",
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        },
        bottomBar = {
            BottomBar(navHostController = navController)
        }
    ) { values ->
        BottomNavGraph(navController = navController, values, context = context)
    }
}

@Composable
fun BottomBar(navHostController: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Scan,
        BottomBarScreen.Setting
    )

    val navB by navHostController.currentBackStackEntryAsState()
    val c = navB?.destination
    val display =
        items.filter { it.route !== BottomBarScreen.Scan.route }.any { it.route === c?.route }
    if (display) {
        NavigationBar(
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.name) },
                    label = { Text(item.name) },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index; navHostController.navigate(item.route) }
                )
            }
        }

    }
}
