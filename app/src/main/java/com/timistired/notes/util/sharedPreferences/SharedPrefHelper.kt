package com.timistired.notes.util.sharedPreferences

import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPrefHelper(private val sharedPreferences: SharedPreferences) : ISharedPrefHelper {

    /**
     * Gets a boolean from shared preferences.
     *
     * @param key the key to reference the value
     * @return the fetched boolean, or false if no value could be found by the provided key
     * */
    override fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    /**
     * Saves a boolean to shared preferences.
     *
     * @param key the key to reference the value
     * @param value the value to save
     * */
    override fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit { putBoolean(key, value) }
    }
}