package com.example.mealplanner.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.data.LoginDataSource
import com.example.mealplanner.data.repo.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val dataSource = LoginDataSource(context)
            return LoginViewModel(
                    loginRepository = LoginRepository(dataSource)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}