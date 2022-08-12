package com.example.mealplanner.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mealplanner.data.model.LoginBody
import com.example.mealplanner.data.model.LoginResponse
import com.example.mealplanner.data.model.User
import com.example.mealplanner.ui.login.LoginStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


object LoginRepository {

    private var dataSource:LoginDataSource? = null

    private var user:User? = null
    private var token:String = ""

    private val _loginStatus = MutableLiveData(LoginStatus.NOT_STARTED)
    val loginStatus : LiveData<LoginStatus>
        get() = _loginStatus

    fun init(dataSource: LoginDataSource){
        this.dataSource = dataSource
    }

    fun login(email:String, password:String){
        _loginStatus.value = LoginStatus.LOADING
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
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("LOGIN_REPO", "Error fetching from remote.")
                    _loginStatus.value = LoginStatus.FAILED
                }
            }
        )
    }

    private fun setLoggedInUser(user: User, token: String){
        this.user = user
        this.token = token
        dataSource!!.storeToken(token)
        this._loginStatus.value = LoginStatus.SUCCESS
    }

    fun logout() {
        user = null
        token = ""
        this._loginStatus.value = LoginStatus.NOT_STARTED
        dataSource!!.logout()
    }

    fun getUserId():Number {
        if(this._loginStatus.value != LoginStatus.SUCCESS && this.user != null){
            throw Exception("No user logged in.")
        }

        return this.user!!.id
    }

    fun getAuthToken():String {
        if(this._loginStatus.value != LoginStatus.SUCCESS && this.token != ""){
            throw Exception("No user logged in.")
        }
        Log.d("LOGIN_REPO", "Returning token: $token")
        return "Bearer ${this.token}"
    }
}