package com.example.mealplanner.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mealplanner.data.model.GroceryItem

@Dao
interface GroceryItemDao {

    @Insert
    fun insert(groceryItem: GroceryItem)

    @Query("select * from grocery_items")
    fun getAllItems():LiveData<List<GroceryItem>>

    @Delete
    fun delete(groceryItem: GroceryItem)

    @Update
    fun update(groceryItem: GroceryItem)
}