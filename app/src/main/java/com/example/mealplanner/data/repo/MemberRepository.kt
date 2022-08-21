package com.example.mealplanner.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mealplanner.data.MealPlannerApi
import com.example.mealplanner.data.model.*
import com.example.mealplanner.data.repo.LoginRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MEMBER_REPO"

class MemberRepository(val household: Household) {

    private val _members = MutableLiveData<List<Member>>(listOf())
    val members:LiveData<List<Member>>
        get() = _members

    private val loginRepo = LoginRepository
    var isOwner : Boolean = false

    init {
        fetchMembers()
    }

    private fun _isOwner( memberships : List<Member>):Boolean{
        val membership = memberships.first { m -> m.user.id == loginRepo.getUserId()}
        return membership.isOwner
    }

    fun fetchMembers() {
        MealPlannerApi.householdService.getMembers(household.id, loginRepo.getAuthToken())
            .enqueue(object: Callback<MembersResponse> {
                override fun onResponse(
                    call: Call<MembersResponse>,
                    res: Response<MembersResponse>
                ) {
                    if(res.isSuccessful){
                        val result = res.body()!!.content
                        isOwner = _isOwner(result)
                        _members.value = result
                    } else {
                        Log.d(TAG, "response: $res")
                    }
                }

                override fun onFailure(call: Call<MembersResponse>, t: Throwable) {
                    throw t
                }
            })
    }

    fun removeMember(userId:Number) {
        MealPlannerApi.householdService.removeMember(household.id, userId, loginRepo.getAuthToken())
            .enqueue(object: Callback<MemberResponse>{
                override fun onResponse(
                    call: Call<MemberResponse>,
                    res: Response<MemberResponse>
                ) {
                    if(res.isSuccessful){
                        val newMembers = _members.value!!.filter{m -> m.user.id != userId}
                        _members.value = newMembers
                    } else {
                        Log.d(TAG, "response: $res")
                    }
                }

                override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                    throw t
                }
            })
    }

    fun makeOwner(userId: Number){
        MealPlannerApi.householdService.makeOwner(household.id, userId, loginRepo.getAuthToken())
            .enqueue(object: Callback<MemberResponse>{
                override fun onResponse(
                    call: Call<MemberResponse>,
                    res: Response<MemberResponse>
                ) {
                    if(res.isSuccessful){
                        Log.d(TAG, "onResponse: Success")
                    } else {
                        Log.d(TAG, "response: $res")
                    }
                }

                override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                    throw t
                }
            })
    }
    
    fun revokeOwner(userId: Number){
        MealPlannerApi.householdService.revokeOwner(household.id, userId, loginRepo.getAuthToken())
            .enqueue(object: Callback<MemberResponse>{
                override fun onResponse(
                    call: Call<MemberResponse>,
                    res: Response<MemberResponse>
                ) {
                    if(res.isSuccessful){
                        Log.d(TAG, "onResponse: Success")
                    } else {
                        Log.d(TAG, "response: $res")
                    }
                }

                override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                    throw t
                }
            })
    }
}