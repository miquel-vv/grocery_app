package com.example.mealplanner.data

import android.util.Log
import com.example.mealplanner.data.model.*
import retrofit2.Response
import java.lang.RuntimeException


private const val TAG = "GROCERY_LIST_REPO"

class GroceryListRepo {

    private val loginRepo = LoginRepository

    suspend fun getGroceryList(households:List<Household>):Set<GroceryItem>{

        val schedules = getSchedules(households)
        val meals = getMeals(schedules)
        val recipeIds = getRecipeIds(meals)
        val ingredients = getIngredients(recipeIds)
        val groceryItems = getGroceryItems(ingredients)

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

        return meals
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
            GroceryItem(i.id, i.id, i.name, i.measures.metric.amount, i.measures.metric.unitLong, GroceryItemStatus.OPEN)
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
}