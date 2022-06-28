package com.timistired.notes.data.location

import com.google.android.gms.tasks.CancellationTokenSource
import com.timistired.notes.data.location.client.ILocationClient
import com.timistired.notes.data.model.Location
import com.timistired.notes.exception.LocationUnavailableException
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class LocationRepository(private val locationClient: ILocationClient) : ILocationRepository {

    /**
     * Gets the current location, if available.
     *
     * @throws LocationUnavailableException if location can not be fetched within a reasonable
     * amount of time, or there is some other error while fetching.
     * */
    override fun getCurrentLocation(): Single<Location> {
        val cancellation = CancellationTokenSource()
        return locationClient.getLocation(cancellation.token)
            .timeout(LOCATION_FETCHING_TIMEOUT_SEC, TimeUnit.SECONDS)
            .doOnError { cancellation.cancel() }
    }

    companion object {
        private const val LOCATION_FETCHING_TIMEOUT_SEC = 10L
    }
}