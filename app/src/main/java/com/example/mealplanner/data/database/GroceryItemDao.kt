package com.example.mealplanner.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mealplanner.data.model.GroceryItem
import com.example.mealplanner.data.model.GroceryItemStatus

@Dao
interface GroceryItemDao {

    @Insert
    fun insert(groceryItem: GroceryItem)

    @Query("select * from grocery_items")
    fun getAllItems():LiveData<List<GroceryItem>>

    @Query("select count(*) from grocery_items")
    fun countItems():Int

    @Query("select * from grocery_items where spoon_id = :spoonId and status = :status")
    fun getBySpoonIdAndStatus(spoonId:Long, status:GroceryItemStatus):GroceryItem

    @Delete
    fun delete(groceryItem: GroceryItem)

    @Update
    fun update(groceryItem: GroceryItem)

    @Query("delete from grocery_items")
    fun clear()
}