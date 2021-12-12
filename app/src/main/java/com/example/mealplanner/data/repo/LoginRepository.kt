package com.example.mealplanner.data.repo

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

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

object LoginRepository {

    private val dataSource = LoginDataSource()
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    val userState = UserState
    var token: String=""
        private set

    fun logout() {
        userState.loggedInUser = null
        dataSource.logout()
    }

    fun login(email: String, password: String, loginResult: MutableLiveData<User>) {
        // handle login
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
                    loginResult.value = user
                    Log.d("TEST", ">>>> Found user: $user")
                }
            } catch(e:Exception){
                Log.d("TEST", ">>>> something went horribly wrong")
            }
        }
    }

    private fun setLoggedInUser(user: User, token: String) {
        userState.loggedInUser = user
        this.token = token
    }

    fun getAuthHeader():String{
        return "Bearer " + this.token
    }
}