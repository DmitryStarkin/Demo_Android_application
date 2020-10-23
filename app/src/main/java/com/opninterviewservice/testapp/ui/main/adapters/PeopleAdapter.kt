package com.opninterviewservice.testapp.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.opninterviewservice.testapp.R
import com.opninterviewservice.testapp.restapi.ShortPersonData
import kotlinx.android.synthetic.main.first_name__item.view.*
import java.util.*


//This File Created at 20.10.2020 17:00.

class PeopleAdapter(
    val people: ArrayList<ShortPersonData>,
    val itemClickListener: (shortPersonInfo: ShortPersonData) -> Unit
) : RecyclerView.Adapter<CatalogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder =
        CatalogViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.first_name__item, parent, false)
        )

    override fun getItemCount(): Int = people.size

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(people[position], itemClickListener)
    }

    fun setData(newPeople: List<ShortPersonData>) {
        val diffCallback = PeopleDiffCallback(people, newPeople)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        people.clear()
        people.addAll(newPeople)
        diffResult.dispatchUpdatesTo(this)
    }
}

class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        item: ShortPersonData,
        itemClickListener: (shortPersonInfo: ShortPersonData) -> Unit
    ) {
        itemView.firstName.text = item.firstName
        itemView.setOnClickListener {
            itemClickListener(item)
        }
    }
}