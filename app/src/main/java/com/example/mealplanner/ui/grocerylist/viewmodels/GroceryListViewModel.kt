package com.example.mealplanner.ui.grocerylist.viewmodels

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.*
import com.example.mealplanner.data.repo.GroceryListRepository
import com.example.mealplanner.data.repo.HouseholdRepository
import com.example.mealplanner.data.model.GroceryItem
import com.example.mealplanner.data.model.GroceryItemStatus
import com.example.mealplanner.data.model.Membership
import kotlinx.coroutines.*

private const val TAG = "GROCERY_LIST_VIEW_MODEL"

class GroceryListViewModel(private val repo:GroceryListRepository,
                           private val householdRepo:HouseholdRepository,
                           application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val households : LiveData<List<Membership>>
        get() = householdRepo.households

    val groceryList : LiveData<List<GroceryItem>>
        get() = repo.groceryList

    fun getHouseholds(){
        householdRepo.fetchHouseholds()
    }

    fun updateGroceryList(list:List<GroceryItem>, memberships:List<Membership>){
        uiScope.launch {
            repo.updateGroceryList(list, memberships.map { m->m.household })
        }
    }

    fun groceryItemChecked(position: Int){
        val item = groceryList.value!![position]
        if(item.status == GroceryItemStatus.OPEN){
            item.status = GroceryItemStatus.SCRAPPED
        } else if(item.status == GroceryItemStatus.SCRAPPED){
            item.status = GroceryItemStatus.OPEN
        }
        uiScope.launch {
            Log.d(TAG, "groceryItemChecked: Saving grocery item.")
            try {
                repo.updateGroceryItem(item)
            } catch (e: SQLiteConstraintException){
                val other = repo.findOtherBySpoonIdAndStatus(item)!!
                other.amount += item.amount
                repo.deleteGroceryItem(item)
                repo.updateGroceryItem(other)
            }
        }
    }

    fun removeGroceryItem(position: Int){
        val item = groceryList.value!![position]
        uiScope.launch{
            repo.deleteGroceryItem(item)
        }
    }

    override fun onCleared(){
        super.onCleared()
        viewModelJob.cancel()
    }
}