package com.example.mealplanner.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R
import com.example.mealplanner.TextItemViewHolder
import com.example.mealplanner.data.model.Household


class HouseholdAdapter:RecyclerView.Adapter<HouseholdAdapter.ViewHolder>() {
    var data = listOf<Household>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = item.name;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.household_list_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.household_name)
        val memberStatus : TextView = itemView.findViewById(R.id.member_status)
    }
}


