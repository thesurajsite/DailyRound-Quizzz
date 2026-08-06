package com.quizapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val FLAME_SLOTS = 4

@Composable
fun StreakIndicator(
    streakLevel: Int,
    showMessage: Boolean,
    message: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(FLAME_SLOTS) { index ->
                FlameSlot(lit = index < streakLevel, index = index)
            }
        }

        if (showMessage) {
            Text(
                text = message,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Composable
private fun FlameSlot(lit: Boolean, index: Int) {
    if (lit) {
        Text(
            text = "🔥",
            modifier =
                Modifier.semantics {
                    contentDescription = "Streak flame ${index + 1} of 4"
                },
            fontSize = 20.sp,
        )
    } else {
        Text(
            text = "○",
            modifier =
                Modifier.semantics {
                    contentDescription = "Empty streak slot ${index + 1} of 4"
                },
            color = Color.White.copy(alpha = 0.3f),
            fontSize = 20.sp,
        )
    }
}
