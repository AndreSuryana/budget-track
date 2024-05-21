package com.andresuryana.budgettrack.ui.statistic.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andresuryana.budgettrack.databinding.ItemCategoryPercentageBinding
import com.andresuryana.budgettrack.domain.adapter.BaseDiffCallback
import com.andresuryana.budgettrack.domain.model.expense.ExpenseCategory

class CategoryPercentAdapter : RecyclerView.Adapter<CategoryPercentViewHolder>() {

    private val list = ArrayList<ExpenseCategory>()

    fun submitList(list: List<ExpenseCategory>) {
        val diffCallback = BaseDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list.clear()
        this.list.addAll(list)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryPercentViewHolder =
        CategoryPercentViewHolder(
            ItemCategoryPercentageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CategoryPercentViewHolder, position: Int) {
        holder.onBind(this.list[position])
    }

    override fun getItemCount(): Int = this.list.size
}