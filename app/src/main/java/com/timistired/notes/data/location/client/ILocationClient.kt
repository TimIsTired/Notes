package com.timistired.notes.data.location.client

import com.google.android.gms.tasks.CancellationToken
import com.timistired.notes.data.model.Location
import io.reactivex.rxjava3.core.Single

interface ILocationClient {

    /**
     * Gets the current location of the device, if available.
     *
     * @param cancellationToken token to cancel the current location request
     * */
    fun getLocation(cancellationToken: CancellationToken): Single<Location>
}