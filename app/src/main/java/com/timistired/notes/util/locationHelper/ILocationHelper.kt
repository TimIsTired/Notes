package com.timistired.notes.util.locationHelper

import android.app.Activity
import com.google.android.gms.common.api.ResolvableApiException

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
}