package com.andresuryana.budgettrack.ui.home.adapter.account

import androidx.recyclerview.widget.RecyclerView
import com.andresuryana.budgettrack.databinding.HorizontalItemSmallBinding
import com.andresuryana.budgettrack.domain.model.account.Account
import com.andresuryana.budgettrack.util.ext.CurrencyFormatter.formatCurrency

class AccountViewHolder(private val binding: HorizontalItemSmallBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: Account) {
        binding.icon.apply {
            setImageResource(item.type.iconRes)
        }
        binding.name.text = item.title
        binding.amount.text = item.amount.formatCurrency()
    }
}