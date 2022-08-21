package com.example.mealplanner.data.repo

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mealplanner.data.MealPlannerApi
import com.example.mealplanner.data.SpoonApi
import com.example.mealplanner.data.database.GroceryItemDao
import com.example.mealplanner.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.RuntimeException


private const val TAG = "GROCERY_LIST_REPO"

class GroceryListRepository(val database:GroceryItemDao, val preferences: SharedPreferences) {

    private val loginRepo = LoginRepository
    private var groceries:Set<GroceryItem>? = null
    private var needsSyncing = true

    val groceryList:LiveData<List<GroceryItem>> = database.getAllItems()

    private suspend fun getGroceryList(households:List<Household>):Set<GroceryItem>{
        if(groceries == null){
            groceries = fetchRemoteGroceryList(households)
        }

        return groceries as Set<GroceryItem>
    }

    suspend fun updateGroceryList(list: List<GroceryItem>, households: List<Household>){
        if(!needsSyncing){
            return
        }
        needsSyncing = false

        val remote = getGroceryList(households)
        Log.d(TAG, "updateGroceryList: Found ${remote.size} new remote items.")
        var updated = 0

        remote.forEach {
            if(!list.contains(it)){
                updated += 1
                saveGroceryItem(it)
            } else if(list.get(list.indexOf(it)).amount != it.amount) {
                updated += 1
                updateGroceryItem(it)
            }
        }

        Log.d(TAG, "updateGroceryList: Updated $updated items.")
    }

    suspend fun updateGroceryItem(item: GroceryItem){
        withContext(Dispatchers.IO){
            database.update(item)
        }
    }

    private suspend fun fetchRemoteGroceryList(households: List<Household>): Set<GroceryItem>{
        val schedules = getSchedules(households)
        val meals = getMeals(schedules)
        val recipeIds = getRecipeIds(meals)
        val ingredients = getIngredients(recipeIds)
        val groceryItems = getGroceryItems(ingredients)

        Log.d(TAG, "fetchRemoteGroceryList: Done getting remote data")
        val editor = preferences.edit()
        editor.putLong("last_update", System.currentTimeMillis())
        editor.apply()

        return consolidateGroceryItems(groceryItems)
    }

    private suspend fun getSchedules(households:List<Household>):List<Schedule>{
        val schedules = mutableListOf<Schedule>()

        households.forEach {
            val scheduleResponse = MealPlannerApi.householdService.getSchedules(it.id, loginRepo.getAuthToken())
            if(scheduleResponse.isSuccessful){
                Log.d(TAG, "fetchMealsForHousehold: Schedules Ok")
                schedules.addAll(scheduleResponse.body()!!.content)
            } else {
                Log.d(TAG, "fetchMealsForHousehold: $scheduleResponse")
                throw RuntimeException(scheduleResponse.body().toString())
            }
        }

        return schedules
    }

    private suspend fun getMeals(schedules:List<Schedule>):List<Meal>{
        val meals = mutableListOf<Meal>()
        schedules.forEach{
            val mealResponse:Response<MealsResponse> = MealPlannerApi.scheduleService.getMeals(it.id, loginRepo.getAuthToken())
            if(mealResponse.isSuccessful){
                Log.d(TAG, "fetchMealsForHousehold: meals ok")
                meals.addAll(mealResponse.body()!!.content)
            } else {
                Log.d(TAG, "fetchMealsForHousehold: $mealResponse")
                throw RuntimeException(mealResponse.body().toString())
            }
        }

        val lastUpdate = preferences.getLong("last_update", 0)

        return meals.filter { it.date.time > lastUpdate }
    }

    private fun getRecipeIds(meals: List<Meal>):List<Number>{
        return meals
            .filter { m -> m.spoontacularId != null }
            .map { m -> m.spoontacularId!!}
    }

    private suspend fun getIngredients(recipeIds:List<Number>):List<Ingredient>{
        val ingredients = mutableListOf<Ingredient>()

        var ingredientResponse:Response<InformationResponse>
        recipeIds.forEach { r ->
            ingredientResponse = SpoonApi.recipeService.getRecipeInformation(r)
            if(ingredientResponse.isSuccessful){
                Log.d(TAG, "fetchMealsForHousehold: Recipes ok")
                ingredients.addAll(ingredientResponse.body()!!.extendedIngredients)
            } else {
                Log.d(TAG, "fetchMealsForHousehold: $ingredientResponse")
                throw RuntimeException(ingredientResponse.body().toString())
            }
        }

        return ingredients
    }

    private fun getGroceryItems(ingredients:List<Ingredient>) : List<GroceryItem>{
        return ingredients.map { i ->
            GroceryItem(i.id, i.name, i.measures.metric.amount, i.measures.metric.unitLong, GroceryItemStatus.OPEN)
        }
    }

    private fun consolidateGroceryItems(items:List<GroceryItem>):Set<GroceryItem>{
        val groceryItems = mutableSetOf<GroceryItem>()
        items.forEach {
            if(groceryItems.contains(it)){
                groceryItems.elementAt(groceryItems.indexOf(it)).amount += it.amount
            } else {
                groceryItems.add(it)
            }
        }
        return groceryItems
    }

    private suspend fun getLocalGroceryList(){
        withContext(Dispatchers.IO){
            database.getAllItems()
        }
    }

    suspend fun saveGroceryList(groceries: Set<GroceryItem>){
        withContext(Dispatchers.IO){
            groceries.forEach {
                database.insert(it)
            }
        }
    }

    suspend fun saveGroceryItem(item: GroceryItem){
        withContext(Dispatchers.IO){
            database.insert(item)
        }
    }
}