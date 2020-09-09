/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.geo

import kotlinx.cinterop.useContents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.Foundation.NSError
import platform.Foundation.NSLog
import platform.darwin.NSObject
import kotlin.native.ref.WeakReference

internal actual class Tracker actual constructor(
    locationsChannel: Channel<LatLng>,
    scope: CoroutineScope
) : NSObject(), CLLocationManagerDelegateProtocol {
    private val coroutineScope = WeakReference(scope)
    private val locationsChannel = WeakReference(locationsChannel)

    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        val locations = didUpdateLocations as List<CLLocation>
        val trackerScope = coroutineScope.get() ?: return
        val locationsChannel = locationsChannel.get() ?: return

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
