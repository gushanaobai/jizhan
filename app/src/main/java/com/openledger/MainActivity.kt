package com.openledger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.openledger.ui.add.AddTransactionScreen
import com.openledger.ui.home.HomeScreen
import com.openledger.ui.theme.OpenLedgerTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * 主 Activity
 *
 * 应用的入口 Activity，使用 Jetpack Compose 构建 UI
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenLedgerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OpenLedgerApp()
                }
            }
        }
    }
}

/**
 * 应用主界面
 *
 * 包含导航逻辑
 */
@Composable
fun OpenLedgerApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToAdd = {
                    navController.navigate("add")
                }
            )
        }
        composable("add") {
            AddTransactionScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}