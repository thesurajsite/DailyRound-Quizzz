package com.quizapp.presentation.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.domain.usecase.CalculateStreakUseCase
import com.quizapp.domain.usecase.GetQuestionsUseCase
import com.quizapp.domain.usecase.SubmitAnswerUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private var autoAdvanceJob: Job? = null

    init {
        loadQuestions()
    }

    fun loadQuestions() {
        _uiState.value = _uiState.value.copy(isLoading = true, loadError = null)
        viewModelScope.launch {
            try {
                val questions = getQuestionsUseCase()
                _uiState.value = _uiState.value.copy(isLoading = false, questions = questions)
            } catch (e: Exception) {
                Log.e("QuizViewModel", "Failed to load questions", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    loadError = "Couldn't load questions. Check your internet connection and tap Retry.",
                )
            }
        }
    }

    fun onOptionSelected(option: String) {
        val state = _uiState.value
        if (state.isAnswerSubmitted || state.selectedOption == option) return

        val question = state.questions[state.currentQuestionIndex]
        val result = submitAnswerUseCase(
            selectedOption = option,
            correctAnswer = question.options[question.correctOptionIndex],
            correctAnswers = state.correctAnswers,
            currentStreak = state.currentStreak,
            highestStreak = state.highestStreak,
        )

        val message = result.milestoneReached?.let { milestone ->
            CalculateStreakUseCase.messageFor(milestone)
        }.orEmpty()

        _uiState.value = _uiState.value.copy(
            selectedOption = option,
            isAnswerSubmitted = true,
            correctAnswers = result.correctAnswers,
            currentStreak = result.currentStreak,
            highestStreak = result.highestStreak,
            streakLevel = result.streakLevel,
            showStreakMessage = result.milestoneReached != null,
            streakMessage = message,
        )

        autoAdvanceJob?.cancel()
        autoAdvanceJob = viewModelScope.launch {
            delay(AUTO_ADVANCE_DELAY_MS)
            goToNextQuestion()
        }
    }

    fun onSkip() {
        val state = _uiState.value
        if (state.isAnswerSubmitted) return
        autoAdvanceJob?.cancel()
        _uiState.value = _uiState.value.copy(
            skippedQuestions = state.skippedQuestions + 1,
            currentStreak = 0,
            streakLevel = 0,
            showStreakMessage = false,
            streakMessage = "",
        )
        goToNextQuestion()
    }

    fun onSwipeNext() {
        val state = _uiState.value
        if (state.isAnswerSubmitted) return
        autoAdvanceJob?.cancel()
        goToNextQuestion()
    }

    fun onSwipePrevious() {
        val state = _uiState.value
        if (state.isAnswerSubmitted || state.currentQuestionIndex == 0) return
        autoAdvanceJob?.cancel()
        _uiState.value = _uiState.value.copy(
            currentQuestionIndex = state.currentQuestionIndex - 1,
            selectedOption = null,
            isAnswerSubmitted = false,
            showStreakMessage = false,
            streakMessage = "",
        )
    }

    fun restartQuiz() {
        autoAdvanceJob?.cancel()
        _uiState.value = QuizUiState()
        loadQuestions()
    }

    private fun goToNextQuestion() {
        val state = _uiState.value
        val nextIndex = state.currentQuestionIndex + 1
        if (nextIndex >= state.questions.size) {
            _uiState.value = state.copy(showResult = true)
        } else {
            _uiState.value = state.copy(
                currentQuestionIndex = nextIndex,
                selectedOption = null,
                isAnswerSubmitted = false,
                showStreakMessage = false,
                streakMessage = "",
            )
        }
    }

    private companion object {
        const val AUTO_ADVANCE_DELAY_MS = 1_000L
    }
}
