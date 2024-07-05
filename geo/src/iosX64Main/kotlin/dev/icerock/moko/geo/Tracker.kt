/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.geo

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.Foundation.NSError
import platform.Foundation.NSLog
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIDevice
import platform.darwin.NSObject
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.ref.WeakReference
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalNativeApi::class, ExperimentalForeignApi::class)
internal actual class Tracker actual constructor(
    locationsChannel: Channel<LatLng>,
    extendedLocationsChannel: Channel<ExtendedLocation>,
    scope: CoroutineScope
) : NSObject(), CLLocationManagerDelegateProtocol {
    private val coroutineScope = WeakReference(scope)
    private val locationsChannel = WeakReference(locationsChannel)
    private val extendedLocationsChannel = WeakReference(extendedLocationsChannel)

    @Suppress("ReturnCount")
    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        val locations = didUpdateLocations as List<CLLocation>
        val trackerScope = coroutineScope.get() ?: return
        val locationsChannel = locationsChannel.get() ?: return
        val extendedLocationsChannel = extendedLocationsChannel.get() ?: return

        locations.forEach { location ->
            val courseAccuracy =
                if (UIDevice.currentDevice.systemVersion.compareTo("13.4") < 0) {
                    null
                } else {
                    location.courseAccuracy
                }

            val latLng = location.coordinate().useContents {
                LatLng(
                    latitude = latitude,
                    longitude = longitude
                )
            }

            val locationPoint = Location(
                coordinates = latLng,
                coordinatesAccuracyMeters = location.horizontalAccuracy
            )

            val speed = Speed(
                speedMps = location.speed,
                speedAccuracyMps = location.speedAccuracy
            )

            val azimuth = Azimuth(
                azimuthDegrees = location.course,
                azimuthAccuracyDegrees = courseAccuracy
            )

            val altitude = Altitude(
                altitudeMeters = location.altitude,
                altitudeAccuracyMeters = location.verticalAccuracy
            )

            val extendedLocation = ExtendedLocation(
                location = locationPoint,
                azimuth = azimuth,
                speed = speed,
                altitude = altitude,
                timestampMs = location.timestamp.timeIntervalSince1970.toLong()
                    .seconds.inWholeMilliseconds
            )

            trackerScope.launch {
                extendedLocationsChannel.send(extendedLocation)
                locationsChannel.send(latLng)
            }
        }
    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
        NSLog("$this fail with $didFailWithError")
    }
}
