package com.quizapp.domain.usecase

import com.quizapp.domain.model.StreakResult

class CalculateStreakUseCase {

    operator fun invoke(
        isCorrect: Boolean,
        currentStreak: Int,
        highestStreak: Int,
    ): StreakResult {
        if (!isCorrect) {
            return StreakResult(
                currentStreak = 0,
                highestStreak = highestStreak,
                streakLevel = 0,
                milestoneReached = null,
            )
        }

        val newStreak = currentStreak + 1
        val newHighest = maxOf(highestStreak, newStreak)
        val milestone = if (newStreak in MILESTONES) newStreak else null

        return StreakResult(
            currentStreak = newStreak,
            highestStreak = newHighest,
            streakLevel = streakLevelFor(newStreak),
            milestoneReached = milestone,
        )
    }

    companion object {
        val MILESTONES = setOf(3, 5, 7, 10)

        fun streakLevelFor(streak: Int): Int = when {
            streak >= 10 -> 4
            streak >= 7 -> 3
            streak >= 5 -> 2
            streak >= 3 -> 1
            else -> 0
        }

        fun messageFor(milestone: Int): String = when (milestone) {
            10 -> "Perfect streak achieved!!"
            else -> "$milestone questions streak achieved!!"
        }
    }
}
