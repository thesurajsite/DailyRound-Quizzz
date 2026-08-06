package com.quizapp.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SubmitAnswerUseCaseTest {

    private val useCase = SubmitAnswerUseCase(CalculateStreakUseCase())

    @Test
    fun `correct answer increments correct count, streak and level`() {
        val result = useCase(
            selectedOption = "Paris",
            correctAnswer = "Paris",
            correctAnswers = 3,
            currentStreak = 2,
            highestStreak = 2,
        )

        assertTrue(result.isCorrect)
        assertEquals(4, result.correctAnswers)
        assertEquals(3, result.currentStreak)
        assertEquals(3, result.highestStreak)
        assertEquals(1, result.streakLevel)
        assertEquals(3, result.milestoneReached)
    }

    @Test
    fun `wrong answer keeps correct count and resets streak`() {
        val result = useCase(
            selectedOption = "Berlin",
            correctAnswer = "Paris",
            correctAnswers = 5,
            currentStreak = 4,
            highestStreak = 6,
        )

        assertFalse(result.isCorrect)
        assertEquals(5, result.correctAnswers)
        assertEquals(0, result.currentStreak)
        assertEquals(0, result.streakLevel)
        assertEquals(6, result.highestStreak)
        assertNull(result.milestoneReached)
    }
}
