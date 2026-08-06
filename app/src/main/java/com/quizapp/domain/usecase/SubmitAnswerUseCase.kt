package com.quizapp.domain.usecase

class SubmitAnswerUseCase(
    private val calculateStreakUseCase: CalculateStreakUseCase,
) {

    data class AnswerResult(
        val isCorrect: Boolean,
        val correctAnswers: Int,
        val currentStreak: Int,
        val highestStreak: Int,
        val streakLevel: Int,
        val milestoneReached: Int?,
    )

    operator fun invoke(
        selectedOption: String,
        correctAnswer: String,
        correctAnswers: Int,
        currentStreak: Int,
        highestStreak: Int,
    ): AnswerResult {
        val isCorrect = selectedOption == correctAnswer
        val streak = calculateStreakUseCase(
            isCorrect = isCorrect,
            currentStreak = currentStreak,
            highestStreak = highestStreak,
        )
        return AnswerResult(
            isCorrect = isCorrect,
            correctAnswers = correctAnswers + if (isCorrect) 1 else 0,
            currentStreak = streak.currentStreak,
            highestStreak = streak.highestStreak,
            streakLevel = streak.streakLevel,
            milestoneReached = streak.milestoneReached,
        )
    }
}
