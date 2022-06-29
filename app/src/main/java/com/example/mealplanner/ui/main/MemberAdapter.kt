package com.example.mealplanner.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R
import com.example.mealplanner.data.model.Member

class MemberAdapter : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    var data = listOf<Member>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.member_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = String.format("%s %s", item.user.firstName, item.user.lastName)
        holder.owner.isChecked = item.isOwner
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.user_name)
        val owner: CheckBox = itemView.findViewById(R.id.owner_status)
    }
}