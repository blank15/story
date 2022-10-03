package com.blank.repository.util

import android.content.SharedPreferences

class SharedPreferenceHelper(private val sharedPreferences: SharedPreferences) {
    fun getSharedPreferences(key: String, defValue: String): String {
        return sharedPreferences.getString(key, defValue)?: ""
    }

    fun setPreferences(key: String, value: String) {
        val e = sharedPreferences.edit()
        e.putString(key, value)
        e.apply()
    }

    fun getSharedPreferences(key: String, defValue:Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    fun setPreferences(key: String, value: Boolean ) {
        val e = sharedPreferences.edit()
        e.putBoolean(key, value)
        e.apply()
    }

}