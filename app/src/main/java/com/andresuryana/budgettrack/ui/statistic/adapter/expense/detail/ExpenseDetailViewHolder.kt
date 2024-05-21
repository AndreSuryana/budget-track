package com.andresuryana.budgettrack.ui.statistic.adapter.expense.detail

import androidx.recyclerview.widget.RecyclerView
import com.andresuryana.budgettrack.databinding.ItemExpenseDetailBinding
import com.andresuryana.budgettrack.domain.model.expense.ExpenseSummaryDetail
import com.andresuryana.budgettrack.util.ext.CurrencyFormatter.formatCurrency
import kotlin.math.abs

class ExpenseDetailViewHolder(private val binding: ItemExpenseDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: ExpenseSummaryDetail) {
        binding.title.text = item.title
        // Make sure the amount is always positive in the UI
        binding.amount.text = abs(item.amount ?: 0L).formatCurrency()
    }
}