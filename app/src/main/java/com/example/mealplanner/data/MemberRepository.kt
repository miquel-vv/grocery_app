package com.example.mealplanner.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mealplanner.data.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberRepository(val household: Household) {

    private val _members = MutableLiveData<List<Member>>(listOf())
    val members:LiveData<List<Member>>
        get() = _members

    private val loginRepo = LoginRepository

    init {
        fetchMembers()
    }

    fun fetchMembers() {
        MealPlannerApi.householdService.getMembers(household.id, loginRepo.getAuthToken())
            .enqueue(object: Callback<MembersResponse> {
                override fun onResponse(
                    call: Call<MembersResponse>,
                    res: Response<MembersResponse>
                ) {
                    Log.d("HOUSEHOLD_REPO", "Got here")
                    if(res.isSuccessful){
                        _members.value = res.body()!!.content
                    } else {
                        Log.d("HOUSEHOLD_REPO", "response: $res")
                    }
                }

                override fun onFailure(call: Call<MembersResponse>, t: Throwable) {
                    throw t
                }
            })
    }
}