package com.example.sneaker_shop.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun SneakerShopTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Purple80,
            secondary = PurpleGrey80,
            tertiary = Pink80
        )
    } else {
        lightColorScheme(
            primary = Purple40,
            secondary = PurpleGrey40,
            tertiary = Pink40
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

@Composable
fun SneakerShopSplashTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = SplashBlue,
            secondary = SplashBlue,
            tertiary = SplashBlue,
            background = SplashBlue,
            surface = SplashBlue,
            primaryContainer = SplashBlue,
            secondaryContainer = SplashBlue
        ),
        content = content
    )
}