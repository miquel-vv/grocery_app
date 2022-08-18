package com.example.mealplanner.ui.households.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R
import com.example.mealplanner.data.model.Member

class MemberAdapter(private val listener: onMemberListener) : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    var data = listOf<Member>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.member_list_item, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = String.format("%s %s", item.user.firstName, item.user.lastName)
        holder.owner.isChecked = item.isOwner
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View, private val listener: onMemberListener) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.user_name)
        val owner: CheckBox = itemView.findViewById(R.id.owner_status)
        val button: ImageView = itemView.findViewById(R.id.delete_member)
        val ownership: CheckBox = itemView.findViewById(R.id.owner_status)

        init {
            button.setOnClickListener {
                listener.deleteMember(adapterPosition)
            }

            ownership.setOnClickListener {
                listener.updateOwnership(adapterPosition, ownership.isChecked)
            }
        }
    }

    interface onMemberListener{
        fun deleteMember(position: Int)
        fun updateOwnership(position: Int, isOwner: Boolean)
    }
}