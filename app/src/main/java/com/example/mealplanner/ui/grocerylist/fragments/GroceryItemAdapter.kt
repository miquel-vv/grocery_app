package com.example.mealplanner.ui.grocerylist.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R
import com.example.mealplanner.data.model.GroceryItem
import java.util.*


private const val TAG = "GROCERY_ITEM_ADAPTER"

class GroceryItemAdapter:RecyclerView.Adapter<GroceryItemAdapter.ViewHolder>() {

    var data = listOf<GroceryItem>()
        set(value){
            field = value
            Log.d(TAG, "Setting new grocery list values")
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name:TextView = itemView.findViewById(R.id.grocery_name)
        val quantity:TextView = itemView.findViewById(R.id.quantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.grocery_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = item.name.replaceFirstChar {c -> c.uppercase()}
        holder.quantity.text = "${item.amount} ${item.unit}"
    }

    override fun getItemCount() = data.size
}