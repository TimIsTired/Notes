package com.timistired.notes

import com.timistired.notes.data.location.LocationRepository
import com.timistired.notes.data.location.client.ILocationClient
import com.timistired.notes.data.model.Location
import com.timistired.notes.exception.LocationUnavailableException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.amshove.kluent.shouldBe
import org.junit.Test
import java.util.concurrent.TimeoutException

class LocationRepositoryTest {

    @Test
    fun `getCurrentLocation fetches from location client`() {
        val location = Location(21.307172, -157.902064)
        val locationClient = mockk<ILocationClient> {
            every { getLocation(any()) } returns Single.just(location)
        }
        val locationRepository = LocationRepository(locationClient)
        val testSingle = locationRepository.getCurrentLocation().test()

        testSingle.assertValue(location)
        testSingle.dispose()

        verify(exactly = 1) { locationClient.getLocation(any()) }
    }

    @Test
    fun `getCurrentLocation cancels fetching on error`() {
        val error = LocationUnavailableException(Exception("Test error"))
        val locationClient = mockk<ILocationClient> {
            every { getLocation(any()) } returns Single.error(error)
        }
        val locationRepository = LocationRepository(locationClient)
        val testSingle = locationRepository.getCurrentLocation().test()

        testSingle.assertError { it is LocationUnavailableException }
        testSingle.dispose()

        verify(exactly = 1) {
            locationClient.getLocation(withArg {
                // make sure that token cancellation has been requested
                it.isCancellationRequested shouldBe true
            })
        }
    }

    @Test
    fun `getCurrentLocation throws exception on timeout`() {
        val locationClient = mockk<ILocationClient> {
            every { getLocation(any()) } returns Single.never()
        }
        val locationRepository = LocationRepository(locationClient)
        val testSingle = locationRepository.getCurrentLocation().test()

        testSingle.await()
        testSingle.assertError { it is TimeoutException }
        testSingle.dispose()
    }
}