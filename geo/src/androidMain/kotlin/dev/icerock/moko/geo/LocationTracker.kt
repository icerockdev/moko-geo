/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.geo

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

actual class LocationTracker(
    actual val permissionsController: PermissionsController,
    interval: Long = 1000,
    priority: Int = LocationRequest.PRIORITY_HIGH_ACCURACY
) : LocationCallback() {
    private var locationProviderClient: FusedLocationProviderClient? = null
    private var isStarted: Boolean = false
    private val locationRequest = LocationRequest().also {
        it.interval = interval
        it.priority = priority
    }
    private val locationsChannel = Channel<LatLng>(Channel.BUFFERED)
    private val trackerScope = CoroutineScope(Dispatchers.Main)

    fun bind(lifecycle: Lifecycle, context: Context, fragmentManager: FragmentManager) {
        permissionsController.bind(lifecycle, fragmentManager)

        locationProviderClient = FusedLocationProviderClient(context)

        if (isStarted) {
            locationProviderClient?.requestLocationUpdates(locationRequest, this, null)
        }

        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                locationProviderClient?.removeLocationUpdates(this@LocationTracker)
                locationProviderClient = null
            }
        })
    }

    override fun onLocationResult(locationResult: LocationResult) {
        super.onLocationResult(locationResult)

        val latLng = LatLng(
            locationResult.lastLocation.latitude,
            locationResult.lastLocation.longitude
        )

        trackerScope.launch { locationsChannel.send(latLng) }
    }

    actual suspend fun startTracking() {
        permissionsController.providePermission(Permission.LOCATION)
        // if permissions request failed - execution stops here

        isStarted = true
        locationProviderClient?.requestLocationUpdates(locationRequest, this, null)
    }

    actual fun stopTracking() {
        isStarted = false
        locationProviderClient?.removeLocationUpdates(this)
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
