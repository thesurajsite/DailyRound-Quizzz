package com.quizapp.domain.model

data class StreakResult(
    val currentStreak: Int,
    val highestStreak: Int,
    val streakLevel: Int,
    val milestoneReached: Int?,
)
