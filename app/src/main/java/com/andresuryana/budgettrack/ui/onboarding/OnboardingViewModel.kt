package com.andresuryana.budgettrack.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresuryana.budgettrack.R
import com.andresuryana.budgettrack.ui.onboarding.manager.OnboardingManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingManager: OnboardingManager
) : ViewModel() {

    private var _onboardingList = MutableLiveData<List<OnboardingItem>>()
    val onboardingList: LiveData<List<OnboardingItem>> = _onboardingList

    init {
        _onboardingList.value = listOf(
            OnboardingItem(
                1,
                R.drawable.ic_launcher_background,
                R.string.onboarding_title_1,
                R.string.onboarding_description_1
            ),
            OnboardingItem(
                2,
                R.drawable.ic_launcher_background,
                R.string.onboarding_title_2,
                R.string.onboarding_description_2
            ),
            OnboardingItem(
                3,
                R.drawable.ic_launcher_background,
                R.string.onboarding_title_3,
                R.string.onboarding_description_3
            ),
        )
    }

    fun markOnboardingShown() {
        viewModelScope.launch {
            onboardingManager.setShowOnboarding(false)
        }
    }
}