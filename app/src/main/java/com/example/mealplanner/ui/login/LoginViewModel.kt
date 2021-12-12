package com.example.mealplanner.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.mealplanner.Observer
import com.example.mealplanner.data.repo.LoginRepository

import com.example.mealplanner.R
import com.example.mealplanner.data.model.User
import com.example.mealplanner.data.state.UserState

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel(), Observer {

    private val userState = UserState

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<User>()
    val loginResult: LiveData<User> = _loginResult

    init {
        userState.addObserver(this)
    }

    fun login(username: String, password: String) {
        loginRepository.login(username, password)
    }

    fun isAuthenticated():Boolean{
        return loginRepository.authenticated()
    }

    fun updateUserFromToken(){
        loginRepository.updateUserFromToken()
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 1
    }

    override fun update() {
        if(userState.loggedInUser != null){
            _loginResult.value = userState.loggedInUser
        }
    }

}