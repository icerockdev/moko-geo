/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.geo

import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.flow.Flow

expect class LocationTracker {
    val permissionsController: PermissionsController

    // can be suspended for request permission
    suspend fun startTracking(allowCoarseLocation: Boolean = false)

    fun stopTracking()

    fun getLocationsFlow(): Flow<LatLng>

    fun getExtendedLocationsFlow(): Flow<ExtendedLocation>
}
