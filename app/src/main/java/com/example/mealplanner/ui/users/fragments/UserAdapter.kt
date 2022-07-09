package com.example.mealplanner.ui.users.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R
import com.example.mealplanner.data.model.User


class UserAdapter(private val onUserListener: OnUserListener) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    var data = listOf<User>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(view, onUserListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = String.format("%s %s", item.firstName, item.lastName)
        holder.email.text = item.email
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View, private val onUserListener: OnUserListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val name: TextView = itemView.findViewById(R.id.user_name)
        val email: TextView = itemView.findViewById(R.id.email)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onUserListener.onUserClick(adapterPosition)
        }
    }

    interface OnUserListener {
        fun onUserClick(position: Int)
    }
}