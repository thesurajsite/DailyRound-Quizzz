package com.quizapp.data.repository

import com.quizapp.data.mappers.toDomain
import com.quizapp.data.source.remote.RetrofitService
import com.quizapp.domain.model.Question
import com.quizapp.domain.repository.QuizRepository

class QuizRepositoryImpl(
    private val service: RetrofitService,
) : QuizRepository {

    private var cachedQuestions: List<Question>? = null

    override suspend fun getQuestions(): List<Question> {
        if (cachedQuestions == null) {
            cachedQuestions = service.getQuestions().map { it.toDomain() }
        }
        return cachedQuestions ?: emptyList()
    }
}
