/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.geo

import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import platform.CoreLocation.CLLocationAccuracy
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLLocationAccuracyBest

actual class LocationTracker(
    actual val permissionsController: PermissionsController,
    accuracy: CLLocationAccuracy = kCLLocationAccuracyBest
) {
    private val locationsChannel = Channel<LatLng>(Channel.BUFFERED)
    private val trackerScope = CoroutineScope(UIDispatcher())
    private val tracker = Tracker(
        locationsChannel = locationsChannel,
        scope = trackerScope
    )
    private val locationManager = CLLocationManager().apply {
        delegate = tracker
        desiredAccuracy = accuracy
    }

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
}
