package com.quizapp.domain.usecase

import com.quizapp.domain.model.Question
import com.quizapp.domain.repository.QuizRepository

class GetQuestionsUseCase(
    private val repository: QuizRepository,
) {
    suspend operator fun invoke(): List<Question> = repository.getQuestions()
}
