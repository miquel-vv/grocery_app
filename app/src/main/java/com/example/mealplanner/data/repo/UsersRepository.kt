package com.example.mealplanner.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mealplanner.data.MealPlannerApi
import com.example.mealplanner.data.model.UserIdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepository {
    private val loginRepo = LoginRepository

    private val _userId = MutableLiveData<Int>()

    val userId : LiveData<Int>
        get() = _userId

    fun searchUserId(email:String){
        MealPlannerApi.userService.getUserId(email, loginRepo.getAuthToken())
            .enqueue(object: Callback<UserIdResponse> {
                override fun onResponse(
                    call: Call<UserIdResponse>,
                    res: Response<UserIdResponse>
                ) {
                    if(res.isSuccessful){
                        _userId.value = res.body()!!.content
                    } else {
                        Log.d("USER_REPO", "response: $res")
                    }
                }

                override fun onFailure(call: Call<UserIdResponse>, t: Throwable) {
                    throw t
                }
            })
    }
}