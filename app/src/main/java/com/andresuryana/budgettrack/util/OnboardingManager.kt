package com.andresuryana.budgettrack.util

import android.content.Context
import android.content.SharedPreferences

class OnboardingManager(context: Context) {

    private val onboardingPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isShowOnboarding(): Boolean {
        return onboardingPrefs.getBoolean(KEY_SHOW_ONBOARDING, true)
    }

    fun setShowOnboarding(isShow: Boolean) {
        onboardingPrefs.edit()
            .putBoolean(KEY_SHOW_ONBOARDING, isShow)
            .apply()
    }

    companion object {
        const val PREFS_NAME = "onboarding_prefs"
        const val KEY_SHOW_ONBOARDING = "show_onboarding"
    }
}