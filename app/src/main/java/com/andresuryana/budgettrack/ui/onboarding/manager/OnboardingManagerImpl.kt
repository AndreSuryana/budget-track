package com.andresuryana.budgettrack.ui.onboarding.manager

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardingManagerImpl @Inject constructor(
    @ApplicationContext context: Context
) : OnboardingManager {

    private val onboardingPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun isShowOnboarding(): Boolean {
        return onboardingPrefs.getBoolean(KEY_SHOW_ONBOARDING, true)
    }

    override fun setShowOnboarding(isShow: Boolean) {
        onboardingPrefs.edit()
            .putBoolean(KEY_SHOW_ONBOARDING, isShow)
            .apply()
    }

    companion object {
        const val PREFS_NAME = "onboarding_prefs"
        const val KEY_SHOW_ONBOARDING = "show_onboarding"
    }
}