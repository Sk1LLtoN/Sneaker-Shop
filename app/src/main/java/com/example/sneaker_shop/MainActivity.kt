package com.example.sneaker_shop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sneaker_shop.ui.theme.SneakerShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            SneakerShopTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "register"
                ) {
                    composable("register") {
                        RegistrationScreen(navController = navController)
                    }
                    composable("signin") {
                        SignIn(navController = navController)
                    }
                    composable("forgotpassword") {
                        ForgotPassword(navController = navController)
                    }
                    composable("verification") {
                        VerificationScreen(navController = navController)
                    }
                    composable("createnewpassword") {
                        CreateNewPassword(navController = navController)
                    }
                }
            }
        }
    }
}