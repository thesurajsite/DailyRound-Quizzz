package com.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.quizapp.presentation.navigation.QuizNavGraph
import com.quizapp.ui.theme.DailyRoundQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyRoundQuizTheme {
                QuizNavGraph()
            }
        }
    }
}
