package com.example.mealplanner.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R
import com.example.mealplanner.TextItemViewHolder
import com.example.mealplanner.data.model.Household


class HouseholdAdapter:RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<Household>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.name;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.household_list_item, parent, false) as TextView
        return TextItemViewHolder(view)
    }
}