package com.quizapp.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.domain.usecase.GetQuestionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                getQuestionsUseCase()
            } catch (e: Exception) {
                Log.e("SplashViewModel", "Failed to load questions", e)
            }
            _isLoading.value = false
        }
    }
}
