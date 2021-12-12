package com.example.mealplanner.data

import android.content.Context
import org.json.JSONException

import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager


class LoginDataSource(private val context: Context) {

    fun storeToken(token:String){
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val edit: SharedPreferences.Editor = prefs.edit()
        try {
            edit.putString("token", token)
            Log.i("Login", token)
            edit.apply()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}