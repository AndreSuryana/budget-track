package com.andresuryana.budgettrack.ui.statistic.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andresuryana.budgettrack.databinding.HorizontalItemSmallBinding
import com.andresuryana.budgettrack.domain.adapter.BaseDiffCallback
import com.andresuryana.budgettrack.domain.model.expense.ExpenseCategory

class CategoryGridAdapter : RecyclerView.Adapter<CategoryGridViewHolder>() {

    private val list = ArrayList<ExpenseCategory>()
    private var onItemClickListener: ((ExpenseCategory) -> Unit)? = null

    fun submitList(list: List<ExpenseCategory>) {
        val diffCallback = BaseDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list.clear()
        this.list.addAll(list)

        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(onItemClickListener: (ExpenseCategory) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryGridViewHolder =
        CategoryGridViewHolder(
            HorizontalItemSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CategoryGridViewHolder, position: Int) {
        val category = this.list[position]
        holder.onBind(category)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(category)
        }
    }

    override fun getItemCount(): Int = this.list.size
}