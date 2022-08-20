package com.example.mealplanner.ui.households.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R
import com.example.mealplanner.data.model.Membership


private const val TAG = "HOUSEHOLD_ADAPTER"

class HouseholdAdapter(private val onHouseholdListener: OnHouseholdListener) : RecyclerView.Adapter<HouseholdAdapter.ViewHolder>() {
    var data = listOf<Membership>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = item.household.name
        if(item.isOwner){
            holder.memberStatus.text = "Owner"
            holder.makeEditable()
        } else {
            holder.memberStatus.text = "Member"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.household_list_item, parent, false)
        return ViewHolder(view, onHouseholdListener)
    }

    class ViewHolder(itemView: View, private val onHouseholdListener: OnHouseholdListener)
        : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.household_name)
        val memberStatus : TextView = itemView.findViewById(R.id.member_status)
        private val deleteButton : ImageView = itemView.findViewById(R.id.delete_household)

        init {

            deleteButton.visibility = View.GONE

            itemView.setOnClickListener {
                onHouseholdListener.onHouseholdClick(adapterPosition)
            }
            deleteButton.setOnClickListener {
                onHouseholdListener.onDeleteClick(adapterPosition)
            }
        }

        fun makeEditable(){
            deleteButton.visibility = View.VISIBLE
        }
    }

    interface OnHouseholdListener{
        fun onHouseholdClick(position:Int)
        fun onDeleteClick(position: Int)
    }
}


