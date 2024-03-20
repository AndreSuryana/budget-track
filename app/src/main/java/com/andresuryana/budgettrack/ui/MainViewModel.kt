package com.andresuryana.budgettrack.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.andresuryana.budgettrack.util.OnboardingManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val onboardingManager = OnboardingManager(application)
    var isReady = false

    init {
        // Simulate loading data asynchronously
        viewModelScope.launch {
            delay(1000)
            isReady = true
        }
    }

    fun isShowOnboarding(): Boolean {
        return onboardingManager.isShowOnboarding()
    }
}