package com.andresuryana.budgettrack.ui.component.animator

import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import com.google.android.material.progressindicator.LinearProgressIndicator

object ObjectAnimUtils {

    fun LinearProgressIndicator.animateProgressTo(progress: Int) {
        // Create an animator to animate the progress changes
        val animator = ObjectAnimator.ofInt(this, "progress", progress)
        animator.duration = 500

        // Configure interpolator to the animation
        animator.interpolator = AccelerateDecelerateInterpolator()

        // Start the animation
        animator.start()
    }
}