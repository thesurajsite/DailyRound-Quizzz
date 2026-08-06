package com.quizapp.data.source.remote

import com.quizapp.data.model.QuestionDto
import retrofit2.http.GET

interface RetrofitService {
    @GET("dr-samrat/53846277a8fcb034e482906ccc0d12b2/raw")
    suspend fun getQuestions(): List<QuestionDto>
}
