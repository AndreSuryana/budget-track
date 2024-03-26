package com.andresuryana.budgettrack.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.andresuryana.budgettrack.databinding.ActivityMainBinding
import com.andresuryana.budgettrack.ui.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSplashScreen()
    }

    private fun setupSplashScreen() {
        // Setup splash screen and loading some data asynchronously
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (viewModel.isReady) {
                        // App ready, start drawing content
                        content.viewTreeObserver.removeOnPreDrawListener(this)

                        // Check should show onboarding on first launch
                        if (viewModel.isShowOnboarding()) showOnboarding()
                        true
                    } else {
                        // App isn't ready yet
                        false
                    }
                }
            }
        )

        // Callback for splash screen exit animation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashView ->
                // Custom slide up animation
                val slideUp = ObjectAnimator.ofFloat(
                    splashView,
                    View.TRANSLATION_Y,
                    0f,
                    -splashView.height.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 200L

                // Remove splash view when animation end
                slideUp.doOnEnd { splashView.remove() }

                // Start animation
                slideUp.start()
            }
        }
    }

    private fun showOnboarding() {
        startActivity(Intent(this, OnboardingActivity::class.java))
        finish()
    }
}