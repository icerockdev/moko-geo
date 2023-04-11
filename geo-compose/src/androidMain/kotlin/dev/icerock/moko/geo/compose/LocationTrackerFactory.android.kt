/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.geo.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationRequest
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.PermissionsController

@Composable
actual fun rememberLocationTrackerFactory(accuracy: LocationTrackerAccuracy): LocationTrackerFactory {
    val context: Context = LocalContext.current
    return remember(context) {
        object : LocationTrackerFactory {
            override fun createLocationTracker(): LocationTracker {
                return LocationTracker(
                    permissionsController = PermissionsController(
                        applicationContext = context.applicationContext
                    ),
                    priority = accuracy.toPriority()
                )
            }

            override fun createLocationTracker(
                permissionsController: PermissionsController
            ): LocationTracker {
                return LocationTracker(
                    permissionsController = permissionsController,
                    priority = accuracy.toPriority()
                )
            }
        }
    }
}

private fun LocationTrackerAccuracy.toPriority(): Int {
    return when (this) {
        LocationTrackerAccuracy.Best -> LocationRequest.PRIORITY_HIGH_ACCURACY
        LocationTrackerAccuracy.Medium -> LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        LocationTrackerAccuracy.LowPower -> LocationRequest.PRIORITY_LOW_POWER
    }
}
