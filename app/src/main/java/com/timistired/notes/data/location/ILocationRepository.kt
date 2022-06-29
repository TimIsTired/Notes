package com.timistired.notes.data.location

import com.timistired.notes.data.model.Location
import io.reactivex.rxjava3.core.Single
import com.timistired.notes.exception.LocationUnavailableException

interface ILocationRepository {

    /**
     * Gets the current location, if available.
     *
     * @throws LocationUnavailableException if location can not be fetched within a reasonable
     * amount of time, or there is some other error while fetching.
     * */
    fun getCurrentLocation(): Single<Location>
}