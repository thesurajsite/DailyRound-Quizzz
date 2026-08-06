package com.quizapp.presentation.quiz

import com.quizapp.domain.model.Question

data class QuizUiState(
    val isLoading: Boolean = true,
    val loadError: String? = null,
    val currentQuestionIndex: Int = 0,
    val questions: List<Question> = emptyList(),
    val selectedOption: String? = null,
    val isAnswerSubmitted: Boolean = false,
    val correctAnswers: Int = 0,
    val skippedQuestions: Int = 0,
    val currentStreak: Int = 0,
    val highestStreak: Int = 0,
    val streakLevel: Int = 0,
    val showStreakMessage: Boolean = false,
    val streakMessage: String = "",
    val showResult: Boolean = false,
)
