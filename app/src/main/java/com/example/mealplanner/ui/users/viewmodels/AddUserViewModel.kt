package com.example.mealplanner.ui.users.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.model.User

class AddUserViewModel(val position: Int) : ViewModel() {

    private val _searchResults = MutableLiveData<List<User>>()
    val searchResults: LiveData<List<User>>
        get() = _searchResults

    fun searchByEmail(email:String){
        _searchResults.value = listOf(User(firstName = "John", lastName = "Doe", email = "john.doe@mail.com"))
    }
}