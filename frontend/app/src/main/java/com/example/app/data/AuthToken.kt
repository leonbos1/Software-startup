package com.example.app.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences


class AuthToken private constructor(context: Context) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: AuthToken? = null

         fun getInstance(context : Context): AuthToken = instance?: synchronized(this){ AuthToken(context).apply { instance = this }
        }

    }
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
    var token: String? = null
        set(value) =sharedPreferences.edit().putString("TOKEN_VALUE",value).apply().also { field = value }
        get() = field ?: sharedPreferences.getString("TOKEN_VALUE", null)
}
