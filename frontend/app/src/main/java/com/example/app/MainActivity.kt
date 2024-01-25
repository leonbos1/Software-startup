package com.example.app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.app.data.AuthToken
import com.example.app.presentation.views.screens.MainScreen
import com.example.app.presentation.views.screens.login
import com.example.app.presentation.views.screens.register
import com.example.app.ui.theme.AndroidComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        actionBar?.hide();
        AuthToken.init(this)


        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color =  Color(0xFFEEF0E5)
                ) {

                    MainScreen()

                }
            }
        }
    }
}