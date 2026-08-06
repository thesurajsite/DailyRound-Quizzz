package com.quizapp.data.mappers

import com.quizapp.data.model.QuestionDto
import com.quizapp.domain.model.Question

fun QuestionDto.toDomain(): Question =
    Question(
        id = id,
        question = question,
        options = options,
        correctOptionIndex = correctOptionIndex,
    )

fun Question.toDto(): QuestionDto =
    QuestionDto(
        id = id,
        question = question,
        options = options,
        correctOptionIndex = correctOptionIndex,
    )
