package com.andresuryana.budgettrack.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresuryana.budgettrack.ui.onboarding.manager.OnboardingManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onboardingManager: OnboardingManager
) : ViewModel() {

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