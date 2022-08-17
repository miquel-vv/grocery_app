package com.example.mealplanner.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mealplanner.data.model.*
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response

private const val TAG = "HOUSEHOLD_REPOSITORY"

object HouseholdRepository {

    private val _households : MutableLiveData<List<Membership>> = MutableLiveData(listOf())
    val households : LiveData<List<Membership>>
        get() = _households

    private val _memberAdded = MutableLiveData<LoadingStatus>(LoadingStatus.NOT_STARTED)
    val memberAdded:LiveData<LoadingStatus>
        get() = _memberAdded

    private val loginRepo = LoginRepository

    fun fetchHouseholds(){
        Log.d(TAG, "Getting households for user: ${loginRepo.getUserId()}")
        MealPlannerApi.householdService.getHouseholds(loginRepo.getUserId(), loginRepo.getAuthToken())
            .enqueue(object: Callback<HouseholdsResponse>{
                override fun onResponse(
                    call: Call<HouseholdsResponse>,
                    res: Response<HouseholdsResponse>
                ) {
                    if(res.isSuccessful){
                        _households.value = res.body()!!.content
                    } else {
                        Log.d(TAG, "response: $res")
                    }
                }

                override fun onFailure(call: Call<HouseholdsResponse>, t: Throwable) {
                    throw t
                }
            })
    }

    fun addMember(userId:Int, householdId:Number){
        _memberAdded.value = LoadingStatus.LOADING
        MealPlannerApi.householdService.addMember(householdId, userId, loginRepo.getAuthToken())
            .enqueue(object: Callback<MemberResponse>{
                override fun onResponse(
                    call: Call<MemberResponse>,
                    res: Response<MemberResponse>
                ) {
                    if(res.isSuccessful){
                        _memberAdded.value = LoadingStatus.SUCCESS
                    } else {
                        _memberAdded.value = LoadingStatus.FAILED
                        Log.d(TAG, "response: $res")
                    }
                }

                override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                    _memberAdded.value = LoadingStatus.FAILED
                    throw t
                }
            })
    }
}