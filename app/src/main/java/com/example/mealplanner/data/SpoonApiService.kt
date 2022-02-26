package com.example.mealplanner.data

import com.example.mealplanner.data.model.InformationResponse
import com.example.mealplanner.data.model.MealsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.spoonacular.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface RecipeService {
    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(@Path("id") spoonId:Number):Response<InformationResponse>
}

object SpoonApi {
    val recipeService : RecipeService by lazy {
        retrofit.create(RecipeService::class.java)
    }
}