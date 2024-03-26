package com.andresuryana.budgettrack.util

import android.content.Context
import android.content.SharedPreferences
import com.andresuryana.budgettrack.ui.onboarding.manager.OnboardingManager
import com.andresuryana.budgettrack.ui.onboarding.manager.OnboardingManagerImpl
import com.andresuryana.budgettrack.ui.onboarding.manager.OnboardingManagerImpl.Companion.KEY_SHOW_ONBOARDING
import com.andresuryana.budgettrack.ui.onboarding.manager.OnboardingManagerImpl.Companion.PREFS_NAME
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class OnboardingManagerTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var sharedPrefs: SharedPreferences

    @Mock
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var onboardingManager: OnboardingManager

    @Before
    fun setUp() {
        // Mock shared preferences
        whenever(context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE))
            .thenReturn(sharedPrefs)
        whenever(sharedPrefs.edit()).thenReturn(editor)
        whenever(editor.putBoolean(anyString(), anyBoolean())).thenReturn(editor)

        // Onboarding manager instance
        onboardingManager = OnboardingManagerImpl(context)
    }

    @Test
    fun `test isShowOnboarding when 'show_onboarding' flag is true`() {
        whenever(sharedPrefs.getBoolean(KEY_SHOW_ONBOARDING, true)).thenReturn(true)
        val isShowOnboarding = onboardingManager.isShowOnboarding()
        assertTrue(isShowOnboarding)
    }

    @Test
    fun `test isShowOnboarding when 'show_onboarding' flag is false`() {
        whenever(sharedPrefs.getBoolean(KEY_SHOW_ONBOARDING, true)).thenReturn(false)
        val isShowOnboarding = onboardingManager.isShowOnboarding()
        assertFalse(isShowOnboarding)
    }

    @Test
    fun `test setShowOnboarding`() {
        val isShowOnboarding = false
        onboardingManager.setShowOnboarding(isShowOnboarding)
        verify(sharedPrefs.edit()).putBoolean(KEY_SHOW_ONBOARDING, isShowOnboarding)
        verify(sharedPrefs.edit()).apply()
    }

    @Test
    fun `test isShowOnboarding when 'show_onboarding' flag is not found, should return true as default`() {
        whenever(sharedPrefs.getBoolean(KEY_SHOW_ONBOARDING, true)).thenReturn(true)
        val isShowOnboarding = onboardingManager.isShowOnboarding()
        assertTrue(isShowOnboarding)
    }
}