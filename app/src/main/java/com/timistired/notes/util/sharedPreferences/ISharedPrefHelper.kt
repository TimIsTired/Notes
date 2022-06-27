package com.timistired.notes.util.sharedPreferences

interface ISharedPrefHelper {

    /**
     * Gets a boolean from shared preferences.
     *
     * @param key the key to reference the value
     * @return the fetched boolean, or false if no value could be found by the provided key
     * */
    fun getBoolean(key: String): Boolean

    /**
     * Saves a boolean to shared preferences.
     *
     * @param key the key to reference the value
     * @param value the value to save
     * */
    fun saveBoolean(key: String, value: Boolean)
}