package com.andresuryana.budgettrack.ui.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andresuryana.budgettrack.R
import com.andresuryana.budgettrack.core.fragment.BaseFragment
import com.andresuryana.budgettrack.databinding.FragmentStatisticBinding
import com.andresuryana.budgettrack.domain.model.expense.ExpenseCategory
import com.andresuryana.budgettrack.domain.model.expense.ExpenseSummaryDetail
import com.andresuryana.budgettrack.domain.model.expense.ExpenseTotal
import com.andresuryana.budgettrack.ui.component.animator.ObjectAnimUtils.animateProgressTo
import com.andresuryana.budgettrack.ui.component.dialog.bottomsheet.DatePickerBottomSheetDialog
import com.andresuryana.budgettrack.ui.statistic.adapter.category.CategoryGridAdapter
import com.andresuryana.budgettrack.ui.statistic.adapter.category.CategoryPercentAdapter
import com.andresuryana.budgettrack.ui.statistic.adapter.expense.detail.ExpenseDetailAdapter
import com.andresuryana.budgettrack.util.ext.CurrencyFormatter.formatCurrency
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class StatisticFragment : BaseFragment() {

    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<StatisticViewModel>()

    private val categoryGridAdapter = CategoryGridAdapter()
    private val categoryPercentAdapter = CategoryPercentAdapter()
    private val expenseDetailAdapter = ExpenseDetailAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSwipeRefreshView()

        setupCategoryGrid()
        setupCategoryPercentList()
        setupExpenseDetailList()

        setupButtonListener()

        observeUiStates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSwipeRefreshView() {
        binding?.root?.setOnRefreshListener {
            viewModel.loadUiData()
            if (binding?.root?.isRefreshing == true) {
                binding?.root?.isRefreshing = false
            }
        }
    }

    private fun setupCategoryGrid() {
        categoryGridAdapter.setOnItemClickListener(this::onCategoryClicked)
        binding?.spendingDetails?.categories?.apply {
            adapter = categoryGridAdapter
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                RecyclerView.HORIZONTAL,
                false
            )
        }
    }

    private fun setupCategoryPercentList() {
        binding?.budgetProgress?.expenses?.apply {
            adapter = categoryPercentAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }
    }

    private fun setupExpenseDetailList() {
        binding?.overallDetails?.details?.apply {
            adapter = expenseDetailAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }
    }

    private fun setupButtonListener() {
        // Export Button
        binding?.budgetReport?.exportPdf?.setOnClickListener {
            // TODO: Should export budgeting report into PDF!
            showToast(requireContext(), "Not yet implemented")
        }

        // Month Filter Button
        binding?.filterMonth?.setOnClickListener { showDatePickerDialog(viewModel.filterDate) }
    }

    private fun observeUiStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    setExpenseTotalCard(it.expenseTotal)
                    setSpendingDetails(it.expenseCategories)
                    setExpenseProgress(it.expenseCategories)
                    setOverallDetails(it.summaryDetails)

                    // Loading
                    showLoadingOverlay(it.isLoading)
                }
            }
        }

        viewModel.toastEvent.observe(viewLifecycleOwner) { event ->
            event.getIfNotHandled()?.let { showToast(requireContext(), it) }
        }
    }

    private fun setExpenseTotalCard(expenseTotal: ExpenseTotal) {
        binding?.expenseTotal?.apply {
            total.text = expenseTotal.total.formatCurrency()
            limitTotal.text = expenseTotal.limit.formatCurrency()
            progressExpense.animateProgressTo(expenseTotal.getProgressPercentage())
        }
    }

    private fun setSpendingDetails(categories: List<ExpenseCategory>) {
        categoryGridAdapter.submitList(categories)
        binding?.spendingDetails?.subtitleSpendingDetails?.text =
            getString(R.string.subtitle_spending_details, categories.size)
        binding?.spendingDetails?.root?.isVisible = categories.isNotEmpty()
    }

    private fun setExpenseProgress(categories: List<ExpenseCategory>) {
        categoryPercentAdapter.submitList(categories)
        binding?.budgetProgress?.root?.isVisible = categories.isNotEmpty()
    }

    private fun setOverallDetails(summaryDetails: List<ExpenseSummaryDetail>) {
        expenseDetailAdapter.submitList(summaryDetails)
        binding?.overallDetails?.root?.isVisible = summaryDetails.isNotEmpty()
    }

    private fun onCategoryClicked(category: ExpenseCategory) {
        showToast(requireContext(), category.title ?: "Nothing to show!")
    }

    private fun showDatePickerDialog(initialDate: Calendar) {
        // Setup the date picker dialog
        val datePicker = DatePickerBottomSheetDialog(initialDate)
        datePicker.hideDayPicker = true
        datePicker.setOnDateChangedListener { year, monthOfYear, _ ->
            viewModel.onDateFilterChanged(monthOfYear, year)
            updateDateFilter(monthOfYear, year)
        }

        // Show date picker dialog
        datePicker.show(parentFragmentManager, datePicker.tag)
    }

    private fun updateDateFilter(monthOfYear: Int, year: Int) {
        // Format displayed month and year into the filter month button
        val months = resources.getStringArray(R.array.months_short)
        val month = months[monthOfYear]

        // Check if the selected month is the current month or last month
        val today = Calendar.getInstance()
        if (monthOfYear == today.get(Calendar.MONTH) && year == today.get(Calendar.YEAR)) {
            binding?.filterMonth?.text = getString(R.string.date_this_month)
        } else if (monthOfYear == today.get(Calendar.MONTH) - 1 && year == today.get(Calendar.YEAR)) {
            binding?.filterMonth?.text = getString(R.string.date_last_month)
        } else {
            binding?.filterMonth?.text = buildString {
                append(month)
                append(" ")
                append(year)
            }
        }
    }
}