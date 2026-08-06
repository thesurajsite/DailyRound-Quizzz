package com.quizapp

import android.app.Application
import com.quizapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class QuizApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@QuizApplication)
            modules(appModule)
        }
    }
}
