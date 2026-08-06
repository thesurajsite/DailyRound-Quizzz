package com.quizapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressSection(
    currentQuestionIndex: Int,
    totalQuestions: Int,
    modifier: Modifier = Modifier,
) {
    val progress = (currentQuestionIndex + 1).toFloat() / totalQuestions.coerceAtLeast(1)

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Question ${currentQuestionIndex + 1} of $totalQuestions",
            color = Color.White.copy(alpha = 0.8f),
        )
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = Color(0xFF31C45D),
            trackColor = Color(0xFF262B33),
        )
    }
}
