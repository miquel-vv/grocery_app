package com.example.mealplanner.data

import android.content.Context
import org.json.JSONException
import com.auth0.jwt.JWT
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.util.*

class LoginDataSource(private val context: Context) {

    fun retrieveToken():String{
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val token = prefs.getString("token", "")
        if(token == null || token==""){
            throw InvalidKeyException()
        }

        val decodedJWT = JWT.decode(token)
        if(decodedJWT.expiresAt.before(Date())){
            throw InvalidKeyException()
        }

        return token
    }

    fun getUserIdFromToken(token:String):Int{
        val decodedJWT = JWT.decode(token)
        val jsonString = String(Base64.getDecoder().decode(decodedJWT.payload), Charset.defaultCharset());

        val user = Gson().fromJson(jsonString, Payload::class.java)
        return user.id
    }

    fun storeToken(token:String){
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val edit: SharedPreferences.Editor = prefs.edit()
        try {
            edit.putString("token", token)
            Log.i("LOGIN_DATA", token)
            edit.apply()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun logout() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val edit: SharedPreferences.Editor = prefs.edit()
        try {
            edit.putString("token", "")
            Log.i("LOGIN_DATA", "")
            edit.apply()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}

data class Payload(val id:Int)