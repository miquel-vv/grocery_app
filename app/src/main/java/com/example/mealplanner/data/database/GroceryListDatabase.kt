package com.example.mealplanner.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mealplanner.data.model.GroceryItem

@Database(entities = [GroceryItem::class], version = 1, exportSchema = false)
abstract class GroceryListDatabase : RoomDatabase(){
    abstract val groceryItemDao : GroceryItemDao

    companion object{
        @Volatile
        private var INSTANCE:GroceryListDatabase? = null

        fun getInstance(context:Context):GroceryListDatabase {
            synchronized(lock = this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GroceryListDatabase::class.java,
                        "grocery_list_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}