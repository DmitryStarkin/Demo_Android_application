package com.opninterviewservice.testapp.ui.main.adapters

import androidx.recyclerview.widget.DiffUtil
import com.opninterviewservice.testapp.restapi.ShortPeopleData


//This File Created at 20.10.2020 17:37.
class PeoplesDiffCallback(
    private val oldList: List<ShortPeopleData>,
    private val newList: List<ShortPeopleData>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id &&
                oldList[oldItemPosition].firstName == newList[newItemPosition].firstName

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id

    }
}