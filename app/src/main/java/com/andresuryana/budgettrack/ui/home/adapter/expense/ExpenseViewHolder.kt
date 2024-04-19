package com.andresuryana.budgettrack.ui.home.adapter.expense

import android.content.res.ColorStateList
import androidx.recyclerview.widget.RecyclerView
import com.andresuryana.budgettrack.R
import com.andresuryana.budgettrack.databinding.ItemExpenseBinding
import com.andresuryana.budgettrack.domain.model.expense.Expense
import com.andresuryana.budgettrack.util.ext.CurrencyFormatter.formatCurrency
import com.andresuryana.budgettrack.util.ext.DateFormatterExt.formatRelativeDate

class ExpenseViewHolder(private val binding: ItemExpenseBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: Expense) {
        binding.icon.apply {
            val iconColor = ColorStateList.valueOf(item.category.type.getIconColor())
            val iconBgColor = iconColor.withAlpha(128)
            setImageResource(item.category.type.iconRes)
            imageTintList = iconColor
            backgroundTintList = iconBgColor
        }
        binding.title.text = item.title
        binding.description.text = itemView.context.getString(
            R.string.placeholder_expense_description,
            item.timestamp?.formatRelativeDate(itemView.context),
            item.description ?: item.category.title
        )
        binding.amount.text = item.amount.formatCurrency()
    }
}
