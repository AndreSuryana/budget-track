package com.andresuryana.budgettrack.ui.home.adapter.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andresuryana.budgettrack.domain.model.account.Account
import com.andresuryana.budgettrack.databinding.ItemAccountBinding
import com.andresuryana.budgettrack.ui.home.adapter.BaseDiffCallback

class AccountGridAdapter : RecyclerView.Adapter<AccountViewHolder>() {

    private val list = ArrayList<Account>()
    private var onItemClickListener: ((Account) -> Unit)? = null

    fun submitList(list: List<Account>) {
        val diffCallback = BaseDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list.clear()
        this.list.addAll(list)

        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(onItemClickListener: (Account) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder =
        AccountViewHolder(
            ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = this.list[position]
        holder.onBind(account)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(account)
        }
    }

    override fun getItemCount(): Int = this.list.size
}