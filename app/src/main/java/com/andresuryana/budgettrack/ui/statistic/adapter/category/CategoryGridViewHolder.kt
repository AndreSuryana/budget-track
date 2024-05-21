package com.andresuryana.budgettrack.ui.statistic.adapter.category

import android.content.res.ColorStateList
import androidx.recyclerview.widget.RecyclerView
import com.andresuryana.budgettrack.R
import com.andresuryana.budgettrack.databinding.HorizontalItemSmallBinding
import com.andresuryana.budgettrack.domain.model.expense.ExpenseCategory
import com.andresuryana.budgettrack.util.ext.CurrencyFormatter.formatCurrency

class CategoryGridViewHolder(private val binding: HorizontalItemSmallBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: ExpenseCategory) {
        binding.icon.apply {
            val iconColor = ColorStateList.valueOf(item.type.getIconColor())
            val iconBgColor = iconColor.withAlpha(128)
            setImageResource(item.type.iconRes)
            imageTintList = iconColor
            backgroundTintList = iconBgColor
        }
        binding.name.text = item.title

        // Format currency as negative value
        binding.amount.text = itemView.context.getString(
            R.string.text_amount_negative, item.spent.formatCurrency()
        )
    }
}