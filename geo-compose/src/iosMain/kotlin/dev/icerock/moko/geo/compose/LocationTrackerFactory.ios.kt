/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.geo.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.PermissionsController
import platform.CoreLocation.CLLocationAccuracy
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.CoreLocation.kCLLocationAccuracyKilometer
import platform.CoreLocation.kCLLocationAccuracyReduced

@Composable
actual fun rememberLocationTrackerFactory(accuracy: LocationTrackerAccuracy): LocationTrackerFactory {
    return remember {
        object : LocationTrackerFactory {
            override fun createLocationTracker(): LocationTracker {
                return LocationTracker(
                    permissionsController = dev.icerock.moko.permissions.ios.PermissionsController(),
                    accuracy = accuracy.toIosAccuracy()
                )
            }

            override fun createLocationTracker(
                permissionsController: PermissionsController
            ): LocationTracker {
                return LocationTracker(
                    permissionsController = permissionsController,
                    accuracy = accuracy.toIosAccuracy()
                )
            }
        }
    }
}

private fun LocationTrackerAccuracy.toIosAccuracy(): CLLocationAccuracy {
    return when (this) {
        LocationTrackerAccuracy.Best -> kCLLocationAccuracyBest
        LocationTrackerAccuracy.Medium -> kCLLocationAccuracyKilometer
        LocationTrackerAccuracy.LowPower -> kCLLocationAccuracyReduced
    }
}
