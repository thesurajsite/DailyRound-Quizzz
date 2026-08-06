package com.quizapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quizapp.domain.model.Question

@Composable
fun QuestionCard(question: Question, modifier: Modifier = Modifier) {
    Text(
        text = question.question,
        modifier = modifier.padding(vertical = 24.dp, horizontal = 8.dp),
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp,
        textAlign = TextAlign.Center,
    )
}
