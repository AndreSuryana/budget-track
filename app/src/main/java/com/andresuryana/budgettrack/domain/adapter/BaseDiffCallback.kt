package com.andresuryana.budgettrack.domain.adapter

import androidx.recyclerview.widget.DiffUtil
import com.andresuryana.budgettrack.domain.model.BaseModel

class BaseDiffCallback(
    private val oldList: List<BaseModel>,
    private val newList: List<BaseModel>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}