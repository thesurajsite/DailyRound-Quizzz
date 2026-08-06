package com.quizapp.presentation.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.quizapp.presentation.quiz.QuizScreen
import com.quizapp.presentation.result.ResultScreen
import com.quizapp.presentation.splash.SplashScreen

@Composable
fun QuizNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainGraph,
    ) {
        mainGraph(navController)
    }
}

private fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation<Screen.MainGraph>(startDestination = Screen.Splash) {
        composable<Screen.Splash> {
            SplashScreen(
                onQuizReady = {
                    navController.navigate(Screen.Quiz) {
                        popUpTo(Screen.Splash) { inclusive = true }
                    }
                },
            )
        }

        composable<Screen.Quiz> {
            QuizScreen(
                onQuizFinished = { correct, total, highest, skipped ->
                    navController.navigate(Screen.Result(correct, highest, skipped, total)) {
                        popUpTo(Screen.Quiz) { inclusive = true }
                    }
                },
            )
        }

        composable<Screen.Result> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.Result>()
            val context = LocalContext.current
            ResultScreen(
                correctAnswers = args.correct,
                highestStreak = args.highest,
                skippedQuestions = args.skipped,
                totalQuestions = args.total,
                onRestart = {
                    navController.navigate(Screen.Quiz) {
                        popUpTo(Screen.Result) { inclusive = true }
                    }
                },
                onClose = {
                    (context as? Activity)?.finish()
                },
            )
        }
    }
}
