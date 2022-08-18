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

    private val _memberAdded = MutableLiveData(LoadingStatus.NOT_STARTED)
    val memberAdded:LiveData<LoadingStatus>
        get() = _memberAdded

    private val _createHouseholdStatus = MutableLiveData(LoadingStatus.NOT_STARTED)
    val createHouseholdStatus:LiveData<LoadingStatus>
        get() = _createHouseholdStatus

    private val _updateHouseholdStatus = MutableLiveData(LoadingStatus.NOT_STARTED)
    val updateHouseholdStatus:LiveData<LoadingStatus>
        get() = _updateHouseholdStatus

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

    fun createHousehold(name:String){
        _createHouseholdStatus.value = LoadingStatus.LOADING
        MealPlannerApi.householdService.createHousehold(loginRepo.getUserId(), loginRepo.getAuthToken(), CreateHousehold(name))
            .enqueue(object: Callback<HouseholdResponse>{
                override fun onResponse(
                    call: Call<HouseholdResponse>,
                    res: Response<HouseholdResponse>
                ) {
                    if(res.isSuccessful){
                        _createHouseholdStatus.value = LoadingStatus.SUCCESS
                    } else {
                        _createHouseholdStatus.value = LoadingStatus.FAILED
                        Log.d(TAG, "onResponse: $res")
                    }
                }

                override fun onFailure(call: Call<HouseholdResponse>, t: Throwable) {
                    _createHouseholdStatus.value = LoadingStatus.FAILED
                    throw t
                }
            })
    }

    fun updateHousehold(householdId:Number, name:String){
        _updateHouseholdStatus.value = LoadingStatus.LOADING
        MealPlannerApi.householdService.updateHousehold(householdId, loginRepo.getAuthToken(), CreateHousehold(name))
            .enqueue(object: Callback<HouseholdResponse>{
                override fun onResponse(
                    call: Call<HouseholdResponse>,
                    res: Response<HouseholdResponse>
                ) {
                    if(res.isSuccessful){
                        _updateHouseholdStatus.value = LoadingStatus.SUCCESS
                    } else {
                        _updateHouseholdStatus.value = LoadingStatus.FAILED
                        Log.d(TAG, "onResponse: $res")
                    }
                }

                override fun onFailure(call: Call<HouseholdResponse>, t: Throwable) {
                    _updateHouseholdStatus.value = LoadingStatus.FAILED
                    throw t
                }
            })
    }

    fun resetCreateStatus(){
        _createHouseholdStatus.value = LoadingStatus.NOT_STARTED
    }

    fun resetUpdateStatus(){
        _updateHouseholdStatus.value = LoadingStatus.NOT_STARTED
    }
}