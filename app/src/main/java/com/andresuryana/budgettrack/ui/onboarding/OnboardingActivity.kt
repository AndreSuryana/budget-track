package com.andresuryana.budgettrack.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.andresuryana.budgettrack.databinding.ActivityOnboardingBinding
import com.andresuryana.budgettrack.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var viewModel: OnboardingViewModel

    // Flag to control auto-sliding
    private var isAutoSlideEnabled = false
    private var isAutoSlideStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[OnboardingViewModel::class.java]

        setupViewPager()
        setupButton()
    }

    override fun onResume() {
        super.onResume()
        if (isAutoSlideStarted) {
            startAutoSlide()
        }
    }

    override fun onPause() {
        super.onPause()
        // Save the auto-slide state before app pause
        isAutoSlideStarted = isAutoSlideEnabled
        stopAutoSlide()

    }

    override fun onDestroy() {
        super.onDestroy()
        stopAutoSlide()
    }

    private fun setupViewPager() {
        // Setup the view pager adapter
        val onboardingAdapter = OnboardingAdapter()
        binding.viewPager.adapter = onboardingAdapter

        // Set view pager target for the indicator
        binding.indicator.setViewPager(binding.viewPager)
        onboardingAdapter.registerAdapterDataObserver(binding.indicator.adapterDataObserver)

        // Observe onboarding list
        viewModel.onboardingList.observe(this, onboardingAdapter::setList)

        // Star auto-sliding
        startAutoSlide()
    }

    private fun setupButton() {
        binding.btnAction.setOnClickListener {
            viewModel.markOnboardingShown()
            showMainScreen()
        }
    }

    private fun showMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun startAutoSlide() {
        isAutoSlideEnabled = true
        lifecycleScope.launch {
            while (isAutoSlideEnabled) {
                delay(AUTO_SLIDE_DELAY)
                withContext(Dispatchers.Main) {
                    var currentPage = binding.viewPager.currentItem
                    if (currentPage == (viewModel.onboardingList.value?.size ?: 0) - 1) {
                        currentPage = 0 // Reset to the first item when reaching the last item
                    } else {
                        currentPage++ // Increment to the next item
                    }
                    binding.viewPager.setCurrentItem(currentPage, true)
                }
                delay(AUTO_SLIDE_PERIOD - AUTO_SLIDE_DELAY)
            }
        }
    }

    private fun stopAutoSlide() {
        isAutoSlideEnabled = false
    }

    companion object {
        // Auto-slide constants
        private const val AUTO_SLIDE_DELAY = 3000L
        private const val AUTO_SLIDE_PERIOD = 5000L
    }
}