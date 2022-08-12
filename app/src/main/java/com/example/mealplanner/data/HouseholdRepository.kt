package com.example.mealplanner.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mealplanner.data.model.*
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response

object HouseholdRepository {

    private val _households : MutableLiveData<List<Membership>> = MutableLiveData(listOf())
    val households : LiveData<List<Membership>>
        get() = _households

    private val loginRepo = LoginRepository

    fun fetchHouseholds(){
        Log.d("HOUSEHOLD_REPO", "Getting households for user: ${loginRepo.getUserId()}")
        MealPlannerApi.householdService.getHouseholds(loginRepo.getUserId(), loginRepo.getAuthToken())
            .enqueue(object: Callback<HouseholdsResponse>{
                override fun onResponse(
                    call: Call<HouseholdsResponse>,
                    res: Response<HouseholdsResponse>
                ) {
                    Log.d("HOUSEHOLD_REPO", "Got here")
                    if(res.isSuccessful){
                        _households.value = res.body()!!.content
                    } else {
                        Log.d("HOUSEHOLD_REPO", "response: $res")
                    }
                }

                override fun onFailure(call: Call<HouseholdsResponse>, t: Throwable) {
                    throw t
                }
            })
    }
}