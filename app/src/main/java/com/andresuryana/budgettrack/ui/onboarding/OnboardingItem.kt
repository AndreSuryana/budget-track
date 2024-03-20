package com.andresuryana.budgettrack.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnboardingItem(

    val id: Int,

    @DrawableRes
    val iconRes: Int,

    @StringRes
    val titleRes: Int,

    @StringRes
    val descriptionRes: Int,
)
