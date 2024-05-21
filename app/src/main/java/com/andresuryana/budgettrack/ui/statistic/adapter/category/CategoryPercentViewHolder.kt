package com.andresuryana.budgettrack.ui.statistic.adapter.category

import androidx.recyclerview.widget.RecyclerView
import com.andresuryana.budgettrack.databinding.ItemCategoryPercentageBinding
import com.andresuryana.budgettrack.domain.model.expense.ExpenseCategory
import com.andresuryana.budgettrack.ui.component.animator.ObjectAnimUtils.animateProgressTo

class CategoryPercentViewHolder(private val binding: ItemCategoryPercentageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: ExpenseCategory) {
        val percentage = item.getProgressPercentage()
        binding.name.text = item.title
        binding.expenseProgress.animateProgressTo(percentage)
        binding.percent.text = String.format("%d%%", percentage)
    }
}