package com.quizapp.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CalculateStreakUseCaseTest {

    private val useCase = CalculateStreakUseCase()

    @Test
    fun `correct answer increments current streak`() {
        val result = useCase(isCorrect = true, currentStreak = 0, highestStreak = 0)

        assertEquals(1, result.currentStreak)
        assertEquals(1, result.highestStreak)
        assertEquals(0, result.streakLevel)
        assertNull(result.milestoneReached)
    }

    @Test
    fun `wrong answer resets current streak and level to zero`() {
        val result = useCase(isCorrect = false, currentStreak = 4, highestStreak = 6)

        assertEquals(0, result.currentStreak)
        assertEquals(0, result.streakLevel)
        assertEquals(6, result.highestStreak)
        assertNull(result.milestoneReached)
    }

    @Test
    fun `highest streak tracks the maximum reached`() {
        val result = useCase(isCorrect = true, currentStreak = 5, highestStreak = 5)

        assertEquals(6, result.currentStreak)
        assertEquals(6, result.highestStreak)
    }

    @Test
    fun `highest streak stays when below previous maximum`() {
        val result = useCase(isCorrect = true, currentStreak = 2, highestStreak = 7)

        assertEquals(3, result.currentStreak)
        assertEquals(7, result.highestStreak)
    }

    @Test
    fun `reaching a streak of 3 lights the first flame and reports the milestone`() {
        val result = useCase(isCorrect = true, currentStreak = 2, highestStreak = 2)

        assertEquals(3, result.currentStreak)
        assertEquals(1, result.streakLevel)
        assertEquals(3, result.milestoneReached)
    }

    @Test
    fun `reaching a streak of 5 lights two flames`() {
        val result = useCase(isCorrect = true, currentStreak = 4, highestStreak = 4)

        assertEquals(5, result.currentStreak)
        assertEquals(2, result.streakLevel)
        assertEquals(5, result.milestoneReached)
    }

    @Test
    fun `reaching a streak of 7 lights three flames`() {
        val result = useCase(isCorrect = true, currentStreak = 6, highestStreak = 6)

        assertEquals(7, result.currentStreak)
        assertEquals(3, result.streakLevel)
        assertEquals(7, result.milestoneReached)
    }

    @Test
    fun `reaching a streak of 10 lights all four flames and reports the milestone`() {
        val result = useCase(isCorrect = true, currentStreak = 9, highestStreak = 9)

        assertEquals(10, result.currentStreak)
        assertEquals(4, result.streakLevel)
        assertEquals(10, result.milestoneReached)
    }

    @Test
    fun `streaks between milestones keep their level without a message`() {
        val streakTwo = useCase(isCorrect = true, currentStreak = 1, highestStreak = 1)
        assertEquals(2, streakTwo.currentStreak)
        assertEquals(0, streakTwo.streakLevel)
        assertNull(streakTwo.milestoneReached)

        val streakFour = useCase(isCorrect = true, currentStreak = 3, highestStreak = 3)
        assertEquals(4, streakFour.currentStreak)
        assertEquals(1, streakFour.streakLevel)
        assertNull(streakFour.milestoneReached)

        val streakSix = useCase(isCorrect = true, currentStreak = 5, highestStreak = 5)
        assertEquals(6, streakSix.currentStreak)
        assertEquals(2, streakSix.streakLevel)
        assertNull(streakSix.milestoneReached)

        val streakNine = useCase(isCorrect = true, currentStreak = 8, highestStreak = 8)
        assertEquals(9, streakNine.currentStreak)
        assertEquals(3, streakNine.streakLevel)
        assertNull(streakNine.milestoneReached)
    }

    @Test
    fun `streak above 10 stays at level four`() {
        val result = useCase(isCorrect = true, currentStreak = 11, highestStreak = 11)

        assertEquals(12, result.currentStreak)
        assertEquals(4, result.streakLevel)
        assertNull(result.milestoneReached)
    }

    @Test
    fun `milestone messages match the spec`() {
        assertEquals("3 questions streak achieved!!", CalculateStreakUseCase.messageFor(3))
        assertEquals("5 questions streak achieved!!", CalculateStreakUseCase.messageFor(5))
        assertEquals("7 questions streak achieved!!", CalculateStreakUseCase.messageFor(7))
        assertEquals("Perfect streak achieved!!", CalculateStreakUseCase.messageFor(10))
    }

    @Test
    fun `milestone set contains 3, 5, 7 and 10`() {
        assertEquals(setOf(3, 5, 7, 10), CalculateStreakUseCase.MILESTONES)
    }
}
