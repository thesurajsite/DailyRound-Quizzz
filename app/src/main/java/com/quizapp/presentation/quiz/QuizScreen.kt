package com.quizapp.presentation.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.quizapp.domain.model.Question
import com.quizapp.presentation.components.OptionButton
import com.quizapp.presentation.components.ProgressSection
import com.quizapp.presentation.components.QuestionCard
import com.quizapp.presentation.components.StreakIndicator
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuizScreen(
    onQuizFinished: (correct: Int, total: Int, highest: Int, skipped: Int) -> Unit,
    viewModel: QuizViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.showResult) {
        if (state.showResult) {
            onQuizFinished(
                state.correctAnswers,
                state.questions.size,
                state.highestStreak,
                state.skippedQuestions,
            )
        }
    }

    when {
        state.isLoading -> {
            LoadingContent()
        }

        state.questions.isEmpty() -> {
            EmptyContent(
                errorMessage = state.loadError,
                onRetry = viewModel::loadQuestions,
            )
        }

        else -> {
            QuizContent(state = state, viewModel = viewModel)
        }
    }
}

@Composable
private fun QuizContent(
    state: QuizUiState,
    viewModel: QuizViewModel,
) {
    val question = state.questions.getOrNull(state.currentQuestionIndex) ?: return
    var totalDrag by remember { mutableStateOf(0f) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color(0xFF101215))
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
                .pointerInput(Unit) {
                    val threshold = 120.dp.toPx()
                    detectHorizontalDragGestures(
                        onDragStart = { totalDrag = 0f },
                        onHorizontalDrag = { _, dragAmount -> totalDrag += dragAmount },
                        onDragEnd = {
                            when {
                                totalDrag < -threshold -> viewModel.onSwipeNext()
                                totalDrag > threshold -> viewModel.onSwipePrevious()
                            }
                        },
                    )
                },
    ) {
        Text(
            text = "Quiz",
            modifier =
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.height(12.dp))

        StreakIndicator(
            streakLevel = state.streakLevel,
            showMessage = state.showStreakMessage,
            message = state.streakMessage,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Spacer(Modifier.height(16.dp))

        ProgressSection(
            currentQuestionIndex = state.currentQuestionIndex,
            totalQuestions = state.questions.size,
        )

        QuestionCard(question = question)

        Spacer(Modifier.height(16.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            question.options.forEach { option ->
                OptionButton(
                    option = option,
                    isSelected = state.selectedOption == option,
                    isRevealed = state.isAnswerSubmitted,
                    isCorrectOption = option == question.options.getOrNull(question.correctOptionIndex),
                    onClick = { viewModel.onOptionSelected(option) },
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = viewModel::onSkip,
            enabled = !state.isAnswerSubmitted,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            shape = RoundedCornerShape(18.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF262B33),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF262B33).copy(alpha = 0.5f),
                    disabledContentColor = Color.White.copy(alpha = 0.5f),
                ),
        ) {
            Text(text = "Skip", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color(0xFF101215)),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = Color(0xFF31C45D))
            Spacer(Modifier.height(16.dp))
            Text(text = "Loading quiz...", color = Color.White)
        }
    }
}

@Composable
private fun EmptyContent(
    errorMessage: String?,
    onRetry: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color(0xFF101215)),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = errorMessage ?: "No questions available",
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            if (errorMessage != null) {
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onRetry,
                    shape = RoundedCornerShape(18.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF262B33),
                            contentColor = Color.White,
                        ),
                ) {
                    Text(text = "Retry", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
