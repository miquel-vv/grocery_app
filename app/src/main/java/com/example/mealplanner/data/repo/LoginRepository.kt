package com.example.mealplanner.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mealplanner.data.LoadingStatus
import com.example.mealplanner.data.LoginDataSource
import com.example.mealplanner.data.MealPlannerApi
import com.example.mealplanner.data.model.LoginBody
import com.example.mealplanner.data.model.LoginResponse
import com.example.mealplanner.data.model.MembershipLink
import com.example.mealplanner.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


object LoginRepository {

    private var dataSource: LoginDataSource? = null

    private var user:User? = null
    private var token:String = ""

    private val _loginStatus = MutableLiveData(LoadingStatus.NOT_STARTED)
    val loadingStatus : LiveData<LoadingStatus>
        get() = _loginStatus

    fun init(dataSource: LoginDataSource){
        LoginRepository.dataSource = dataSource
    }

    fun login(email:String, password:String){
        _loginStatus.value = LoadingStatus.LOADING
        var user:User?
        var token = ""
        val body = LoginBody(email, password)

        MealPlannerApi.loginService.login(body).enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    res: Response<LoginResponse>
                ) {
                    if(res.isSuccessful){
                        user = res.body()?.content?.user!!
                        token = res.body()?.content?.token!!
                        setLoggedInUser(user!!, token)
                        Log.d("LOGIN_REPO", ">>>> Found user: $user")
                    } else {
                        _loginStatus.value = LoadingStatus.FAILED
                        Log.d("LOGIN_REPO", "$res")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("LOGIN_REPO", "Error fetching from remote.")
                    _loginStatus.value = LoadingStatus.FAILED
                }
            }
        )
    }

    private fun setLoggedInUser(user: User, token: String){
        LoginRepository.user = user
        LoginRepository.token = token
        dataSource!!.storeToken(token)
        _loginStatus.value = LoadingStatus.SUCCESS
    }

    fun logout() {
        user = null
        token = ""
        _loginStatus.value = LoadingStatus.NOT_STARTED
        dataSource!!.logout()
    }

    fun getUserId():Number {
        if(_loginStatus.value != LoadingStatus.SUCCESS && user != null){
            throw Exception("No user logged in.")
        }

        return user!!.id
    }

    fun getAuthToken():String {
        if(_loginStatus.value != LoadingStatus.SUCCESS && token != ""){
            throw Exception("No user logged in.")
        }
        Log.d("LOGIN_REPO", "Returning token: $token")
        return "Bearer $token"
    }

    fun getHouseholds():Array<MembershipLink>{
        return user!!.households
    }
}