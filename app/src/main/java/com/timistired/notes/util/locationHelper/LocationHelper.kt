package com.timistired.notes.util.locationHelper

import android.app.Activity
import android.content.IntentSender
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.timistired.notes.util.log.ILogger
import org.koin.ext.getFullName

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
}