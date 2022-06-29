package com.timistired.notes.util.locationHelper

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.common.api.ResolvableApiException
import com.timistired.notes.data.model.Location

interface ILocationHelper {

    /**
     * Shows a dialog to the user, asking to enable location settings.
     *
     * @param activity an activity to access the settings client
     * @param successCallback callback to invoke for success-case
     * @param errorCallback callback to invoke for error-case
     * */
    fun showLocationSettingsDialog(
        activity: Activity,
        successCallback: () -> Unit,
        errorCallback: (resolvable: ResolvableApiException) -> Unit
    )

    /**
     * Checks if Google Maps App is available on this device.
     *
     * @param context a [Context] to access the package manager
     * @return true if available, else false
     * */
    fun isGoogleMapsAvailable(context: Context): Boolean

    /**
     * Gets an [Intent] to launch Google Maps with a given pre-filled location.
     *
     * @param location the location to pass to Google Maps
     * @return the created [Intent]
     * */
    fun getGoogleMapsIntent(location: Location): Intent

    /**
     * Gets an [Intent] to launch the device's default Maps app, as a fallback solution.
     *
     * @param location the location to pass to the Maps app
     * @return the created [Intent]
     * */
    fun getGenericMapsIntent(location: Location): Intent
}