package com.quizapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object MainGraph : Screen()

    @Serializable
    data object Splash : Screen()

    @Serializable
    data object Quiz : Screen()

    @Serializable
    data class Result(
        val correct: Int,
        val highest: Int,
        val skipped: Int,
        val total: Int,
    ) : Screen()
}
