package com.timistired.notes.util.locationHelper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.timistired.notes.data.model.Location
import com.timistired.notes.util.log.ILogger
import org.koin.ext.getFullName
import java.util.*

class LocationHelper(private val logger: ILogger) : ILocationHelper {

    /**
     * Shows a dialog to the user, asking to enable location settings.
     *
     * @param activity an activity to access the settings client
     * @param successCallback callback to invoke for success-case
     * @param errorCallback callback to invoke for error-case
     * */
    override fun showLocationSettingsDialog(
        activity: Activity,
        successCallback: () -> Unit,
        errorCallback: (resolvable: ResolvableApiException) -> Unit
    ) {
        val locationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val task: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build())

        task.addOnCompleteListener {
            try {
                it.getResult(ApiException::class.java)
                successCallback.invoke()
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            val resolvable: ResolvableApiException =
                                exception as ResolvableApiException

                            errorCallback.invoke(resolvable)
                        } catch (error: IntentSender.SendIntentException) {
                            // ignore
                        } catch (error: ClassCastException) {
                            // ignore
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        logger.logMessage(
                            tag = this::class.getFullName(),
                            debugMessage = "Cannot show settings dialog"
                        )
                    }
                }
            }
        }
    }

    /**
     * Checks if Google Maps App is available on this device.
     *
     * @param context a [Context] to access the package manager
     * @return true if available, else false
     * */
    override fun isGoogleMapsAvailable(context: Context): Boolean {
        return try {
            val info = context.packageManager.getApplicationInfo(GOOGLE_MAPS_PACKAGE, 0)
            info.enabled
        } catch (error: PackageManager.NameNotFoundException) {
            false
        }
    }

    /**
     * Gets an [Intent] to launch Google Maps with a given pre-filled location.
     *
     * @param location the location to pass to Google Maps
     * @return the created [Intent]
     * */
    override fun getGoogleMapsIntent(location: Location): Intent {
        val uri = Uri.parse("geo:${location.latitude},${location.longitude}")
        return Intent(Intent.ACTION_VIEW, uri).apply {
            `package` = GOOGLE_MAPS_PACKAGE
        }
    }

    /**
     * Gets an [Intent] to launch the device's default Maps app, as a fallback solution.
     *
     * @param location the location to pass to the Maps app
     * @return the created [Intent]
     * */
    override fun getGenericMapsIntent(location: Location): Intent {
        val uri = String.format(Locale.ENGLISH, "geo:${location.latitude},${location.longitude}")
        return Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    }

    companion object {
        private const val GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps"
    }
}