package com.example.mealplanner.data.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.mealplanner.data.model.GroceryItem
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GroceryListDatabaseTest {

    private lateinit var groceryDao: GroceryItemDao
    private lateinit var database: GroceryListDatabase

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        database = Room.inMemoryDatabaseBuilder(context, GroceryListDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        groceryDao = database.groceryItemDao
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase(){
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetGroceryItem(){
        val item = GroceryItem(1111, "testItem", 5F, "gram")
        groceryDao.insert(item)
        var size = groceryDao.countItems()
        assertEquals(size, 1)
        val queriedItem = groceryDao.getBySpoonIdAndStatus(item.spoonId, item.status)
        assertEquals(item, queriedItem)

        groceryDao.delete(queriedItem)
        size = groceryDao.countItems()
        assertEquals(size, 0)
    }
}