package com.timistired.notes.data.location.client

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.timistired.notes.data.model.Location
import com.timistired.notes.exception.LocationUnavailableException
import com.timistired.notes.util.log.ILogger
import io.reactivex.rxjava3.core.Single
import org.koin.ext.getFullName

/**
 * [ILocationClient] implementation that uses the fused location provider.
 * */
class LocationClient(
    private val fusedClient: FusedLocationProviderClient,
    private val logger: ILogger
) : ILocationClient {

    /**
     * Gets the current location of the device, if available.
     *
     * @param cancellationToken token to cancel the current location request
     * @throws LocationUnavailableException if location could not be fetched
     * */
    @SuppressLint("MissingPermission") // permission already checked at this point
    override fun getLocation(
        cancellationToken: CancellationToken
    ): Single<Location> = Single.create { emitter ->
        fusedClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationToken
        ).run {
            addOnSuccessListener { result ->
                if (isSuccessful && result != null) {
                    val location = Location(
                        latitude = result.latitude,
                        longitude = result.longitude
                    )
                    logger.logMessage(
                        tag = this::class.getFullName(),
                        debugMessage = "new location fetched: $location"
                    )
                    emitter.onSuccess(location)
                } else {
                    exception?.let { emitter.onError(it) }
                }
            }
            addOnFailureListener { error ->
                emitter.onError(LocationUnavailableException(error))
            }
        }
    }
}