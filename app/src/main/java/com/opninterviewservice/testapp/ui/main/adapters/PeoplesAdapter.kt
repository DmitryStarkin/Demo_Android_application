package com.opninterviewservice.testapp.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.opninterviewservice.testapp.R
import com.opninterviewservice.testapp.restapi.ShortPeopleData
import kotlinx.android.synthetic.main.first_name__item.view.*
import java.util.*


//This File Created at 20.10.2020 17:00.

class PeoplesAdapter(
    val peoples: ArrayList<ShortPeopleData>,
    val itemClickListener: (shortPeopleInfo: ShortPeopleData) -> Unit
) : RecyclerView.Adapter<CatalogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder =
        CatalogViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.first_name__item, parent, false)
        )

    override fun getItemCount(): Int = peoples.size

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(peoples[position], itemClickListener)
    }

    fun setData(newPeoples: List<ShortPeopleData>) {
        val diffCallback = PeoplesDiffCallback(peoples, newPeoples)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        peoples.clear()
        peoples.addAll(newPeoples)
        diffResult.dispatchUpdatesTo(this)
    }
}

class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        item: ShortPeopleData,
        itemClickListener: (shortPeopleInfo: ShortPeopleData) -> Unit
    ) {
        itemView.firstName.text = item.firstName
        itemView.setOnClickListener {
            itemClickListener(item)
        }
    }
}