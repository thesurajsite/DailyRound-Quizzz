package com.quizapp.di

import com.quizapp.data.repository.QuizRepositoryImpl
import com.quizapp.data.source.remote.RetrofitService
import com.quizapp.domain.repository.QuizRepository
import com.quizapp.domain.usecase.CalculateStreakUseCase
import com.quizapp.domain.usecase.GetQuestionsUseCase
import com.quizapp.domain.usecase.SubmitAnswerUseCase
import com.quizapp.presentation.quiz.QuizViewModel
import com.quizapp.presentation.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule =
    module {
        single<RetrofitService> { provideQuizApi() }
        single<QuizRepository> { QuizRepositoryImpl(get()) }

        factory { GetQuestionsUseCase(get()) }
        factory { CalculateStreakUseCase() }
        factory { SubmitAnswerUseCase(get()) }

        viewModel { QuizViewModel(get(), get()) }
        viewModel { SplashViewModel(get()) }
    }

private const val QUIZ_API_BASE_URL = "https://gist.githubusercontent.com/"

private fun provideQuizApi(): RetrofitService =
    Retrofit.Builder()
        .baseUrl(QUIZ_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitService::class.java)
