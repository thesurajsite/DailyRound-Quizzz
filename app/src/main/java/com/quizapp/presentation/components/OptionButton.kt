package com.quizapp.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun OptionButton(
    option: String,
    isSelected: Boolean,
    isRevealed: Boolean,
    isCorrectOption: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shake = remember { Animatable(0f) }
    val haptic = LocalHapticFeedback.current

    val buttonColor =
        when {
            isRevealed && isCorrectOption -> Color(0xFF31C45D)
            isRevealed && isSelected -> Color(0xFFB61D1D)
            else -> Color(0xFF262B33)
        }

    LaunchedEffect(isRevealed, isSelected) {
        if (isRevealed && isSelected && !isCorrectOption) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            shake.snapTo(0f)
            shake.animateTo(
                targetValue = 0f,
                animationSpec =
                    keyframes {
                        durationMillis = 450
                        -12f at 90
                        12f at 180
                        -8f at 270
                        8f at 360
                        0f at 450
                    },
            )
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(buttonColor)
                .graphicsLayer {
                    translationX = shake.value
                }
                .clickable(
                    enabled = !isRevealed,
                    onClick = onClick,
                )
                .semantics { selected = isSelected },
    ) {
        Text(
            text = option,
            modifier =
                Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp),
            color = Color.White,
            fontWeight =
                if (isRevealed && (isCorrectOption || isSelected)) {
                    FontWeight.Bold
                } else {
                    FontWeight.Normal
                },
            maxLines = 1,
        )
    }
}
