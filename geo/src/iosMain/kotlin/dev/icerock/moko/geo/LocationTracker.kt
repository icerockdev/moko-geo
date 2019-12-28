/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.geo

import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.cinterop.useContents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationAccuracy
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.Foundation.NSLog
import platform.darwin.NSObject
import kotlin.native.ref.WeakReference

actual class LocationTracker(
    actual val permissionsController: PermissionsController,
    accuracy: CLLocationAccuracy = kCLLocationAccuracyBest
) {
    private val tracker = Tracker(this)
    private val locationManager = CLLocationManager().apply {
        delegate = tracker
        desiredAccuracy = accuracy
    }
    private val locationsChannel = Channel<LatLng>(Channel.BUFFERED)
    private val trackerScope = CoroutineScope(UIDispatcher())

    actual suspend fun startTracking() {
        permissionsController.providePermission(Permission.LOCATION)
        // if permissions request failed - execution stops here

        locationManager.startUpdatingLocation()
    }

    actual fun stopTracking() {
        locationManager.stopUpdatingLocation()
    }

    actual fun getLocationsFlow(): Flow<LatLng> {
        return channelFlow {
            val sendChannel = channel
            val job = launch {
                while (isActive) {
                    val latLng = locationsChannel.receive()
                    sendChannel.send(latLng)
                }
            }

            awaitClose { job.cancel() }
        }
    }

    private class Tracker(
        locationTracker: LocationTracker
    ) : NSObject(), CLLocationManagerDelegateProtocol {
        private val locationTracker = WeakReference(locationTracker)

        override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
            val locations = didUpdateLocations as List<CLLocation>
            val locationTracker = locationTracker.get() ?: return
            val trackerScope = locationTracker.trackerScope
            val locationsChannel = locationTracker.locationsChannel

            locations.forEach { location ->
                val latLng = location.coordinate().useContents {
                    LatLng(
                        latitude = latitude,
                        longitude = longitude
                    )
                }
                trackerScope.launch { locationsChannel.send(latLng) }
            }
        }

        override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
            NSLog("$this fail with $didFailWithError")
        }
    }
}

