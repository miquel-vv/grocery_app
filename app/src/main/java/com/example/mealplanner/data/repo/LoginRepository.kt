package com.example.mealplanner.data.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mealplanner.data.LoginDataSource
import com.example.mealplanner.data.MealPlannerApi
import com.example.mealplanner.data.model.LoginBody
import com.example.mealplanner.data.model.LoginResponse
import com.example.mealplanner.data.model.User
import com.example.mealplanner.data.state.UserState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import java.security.InvalidKeyException

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(private val dataSource: LoginDataSource){

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )
    private val userState = UserState

    fun logout() {
        userState.loggedInUser = null
        userState.token=""
        dataSource.logout()
    }

    fun authenticated():Boolean{
        val token:String
        try{
            token = dataSource.retrieveToken()
            userState.token = token
        } catch (e: InvalidKeyException){
            return false
        }
        return true
    }

    fun updateUserFromToken(){
        val id = dataSource.getUserIdFromToken(userState.token)
        Log.d("TEST", "Found user with id: " + id)
        coroutineScope.launch {
            try{
                val res = MealPlannerApi.loginService.get(id, userState.getAuthHeader())
                if(res.isSuccessful){
                    val user = res.body()!!.content
                    Log.d("USERREPO", "User found : " + user.firstName)
                    userState.loggedInUser = user
                } else {
                    throw Exception()
                }
            } catch (e:Exception){

            }
        }
    }

    fun login(email: String, password: String) {
        coroutineScope.launch {
            var user:User? = null
            var token:String = ""
            val body = LoginBody(email, password)
            Log.d("TEST", ">>> body created")
            try {
                val res:Response<LoginResponse> = MealPlannerApi.loginService.login(body)
                if(res.isSuccessful){
                    user = res.body()?.content?.user!!
                    token = res.body()?.content?.token!!
                    setLoggedInUser(user, token)
                    Log.d("TEST", ">>>> Found user: $user")
                    dataSource.storeToken(token)
                }
            } catch(e:Exception){
                Log.d("TEST", ">>>> something went horribly wrong")
            }
        }
    }

    private fun setLoggedInUser(user: User, token: String) {
        userState.loggedInUser = user
        userState.token = token
    }

}