package com.quizapp.domain.repository

import com.quizapp.domain.model.Question

interface QuizRepository {
    suspend fun getQuestions(): List<Question>
}
