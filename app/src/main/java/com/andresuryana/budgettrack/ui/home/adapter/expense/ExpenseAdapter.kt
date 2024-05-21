package com.andresuryana.budgettrack.ui.home.adapter.expense

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andresuryana.budgettrack.databinding.ItemExpenseBinding
import com.andresuryana.budgettrack.domain.model.expense.Expense
import com.andresuryana.budgettrack.domain.adapter.BaseDiffCallback

class ExpenseAdapter : RecyclerView.Adapter<ExpenseViewHolder>() {

    private val list = ArrayList<Expense>()
    private var onItemClickListener: ((Expense) -> Unit)? = null

    fun submitList(list: List<Expense>) {
        val diffCallback = BaseDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list.clear()
        this.list.addAll(list)

        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(onItemClickListener: (Expense) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder =
        ExpenseViewHolder(
            ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = this.list[position]
        holder.onBind(expense)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(expense)
        }
    }

    override fun getItemCount(): Int = this.list.size
}