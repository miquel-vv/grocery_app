package com.example.mealplanner.ui.grocerylist.fragments

import android.graphics.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R
import com.example.mealplanner.data.model.GroceryItem
import com.example.mealplanner.data.model.GroceryItemStatus
import java.util.*


private const val TAG = "GROCERY_ITEM_ADAPTER"

class GroceryItemAdapter(val listener:OnGroceryItemClickListener):RecyclerView.Adapter<GroceryItemAdapter.ViewHolder>() {

    var data = listOf<GroceryItem>()
        set(value){
            field = value
            Log.d(TAG, "Setting new grocery list values")
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: View, listener:OnGroceryItemClickListener):RecyclerView.ViewHolder(itemView){
        val name:TextView = itemView.findViewById(R.id.grocery_name)
        val quantity:TextView = itemView.findViewById(R.id.quantity)
        private val icon:ImageView = itemView.findViewById(R.id.grocery_icon)
        private val delete:ImageView = itemView.findViewById(R.id.delete_item)

        init {
            setOpen()
            itemView.setOnClickListener {
                listener.onGroceryItemClicked(adapterPosition)
            }

            delete.setOnClickListener {
                listener.onDeleteGroceryItem(adapterPosition)
            }
        }

        fun setOpen(){
            name.paintFlags = name.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            icon.colorFilter = PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
        }

        fun setScrapped(){
            name.paintFlags = name.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            icon.colorFilter = PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.grocery_list_item, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = item.name.replaceFirstChar {c -> c.uppercase()}
        holder.quantity.text = "${item.amount} ${item.unit}"
        if(item.status == GroceryItemStatus.SCRAPPED){
            holder.setScrapped()
        }
    }

    override fun getItemCount() = data.size

    interface OnGroceryItemClickListener {
        fun onGroceryItemClicked(position: Int)
        fun onDeleteGroceryItem(position: Int)
    }
}