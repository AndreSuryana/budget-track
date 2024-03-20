package com.andresuryana.budgettrack.ui.onboarding

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import androidx.viewpager2.widget.ViewPager2
import com.andresuryana.budgettrack.R
import com.andresuryana.budgettrack.databinding.ActivityOnboardingBinding
import com.andresuryana.budgettrack.ui.MainActivity
import com.andresuryana.budgettrack.ui.onboarding.ActionButtonState.GET_STARTED
import com.andresuryana.budgettrack.ui.onboarding.ActionButtonState.NEXT

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var viewModel: OnboardingViewModel

    private lateinit var onboardingAdapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[OnboardingViewModel::class.java]

        onboardingAdapter = OnboardingAdapter()

        setupViewPager()
        setupButton()
    }

    private fun setupViewPager() {
        // Setup the view pager with the adapter and page listener
        binding.viewPager.adapter = onboardingAdapter
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == onboardingAdapter.itemCount - 1) {
                        setActionButtonState(GET_STARTED)
                    } else {
                        setActionButtonState(NEXT)
                    }
                }
            }
        )

        // Set view pager target for the indicator
        binding.indicator.setViewPager(binding.viewPager)
        onboardingAdapter.registerAdapterDataObserver(binding.indicator.adapterDataObserver)

        // Observe onboarding list
        viewModel.onboardingList.observe(this, onboardingAdapter::setList)
    }

    private fun setupButton() {
        binding.btnSkip.setOnClickListener { handleSkipButtonClick() }
        binding.btnAction.setOnClickListener { handleActionButtonClick() }
    }

    private fun handleSkipButtonClick() {
        viewModel.markOnboardingShown()
        showMainScreen()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleActionButtonClick() {
        val currentItem = binding.viewPager.currentItem
        val lastItemIndex = onboardingAdapter.itemCount - 1

        if (currentItem == lastItemIndex) {
            // Handle action when on last item
            viewModel.markOnboardingShown()
            showMainScreen()
        } else {
            // Handle action when not on last item
            if (currentItem < lastItemIndex) {
                // Move to the next page and notify adapter
                binding.viewPager.currentItem = currentItem + 1
                onboardingAdapter.notifyDataSetChanged()
                setActionButtonState(if (currentItem + 1 == lastItemIndex) GET_STARTED else NEXT)
            }
        }
    }

    private fun setActionButtonState(state: ActionButtonState) {
        val constraintSet = ConstraintSet().apply { clone(binding.root) }

        when (state) {
            NEXT -> {
                binding.btnAction.setText(R.string.btn_next)
                binding.btnAction.contentDescription = getString(R.string.btn_next)
                constraintSet.setHorizontalBias(R.id.btn_action, 1f)
            }

            GET_STARTED -> {
                binding.btnAction.setText(R.string.btn_get_started)
                binding.btnAction.contentDescription = getString(R.string.btn_get_started)
                constraintSet.setHorizontalBias(R.id.btn_action, 0.5f)
            }
        }

        applyConstraintSetWithAnimation(constraintSet)
    }

    private fun applyConstraintSetWithAnimation(constraintSet: ConstraintSet) {
        val transition = ChangeBounds().apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 500
        }

        TransitionManager.beginDelayedTransition(binding.root, transition)
        constraintSet.applyTo(binding.root)
    }

    private fun showMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}