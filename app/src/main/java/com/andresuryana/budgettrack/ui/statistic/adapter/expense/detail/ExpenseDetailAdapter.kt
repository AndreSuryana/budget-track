package com.andresuryana.budgettrack.ui.statistic.adapter.expense.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andresuryana.budgettrack.databinding.ItemExpenseDetailBinding
import com.andresuryana.budgettrack.domain.adapter.BaseDiffCallback
import com.andresuryana.budgettrack.domain.model.expense.ExpenseSummaryDetail

class ExpenseDetailAdapter : RecyclerView.Adapter<ExpenseDetailViewHolder>() {

    private val list = ArrayList<ExpenseSummaryDetail>()

    fun submitList(list: List<ExpenseSummaryDetail>) {
        val diffCallback = BaseDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list.clear()
        this.list.addAll(list)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseDetailViewHolder =
        ExpenseDetailViewHolder(
            ItemExpenseDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ExpenseDetailViewHolder, position: Int) {
        holder.onBind(this.list[position])
    }

    override fun getItemCount(): Int = this.list.size
}