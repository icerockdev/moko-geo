package dev.icerock.moko.geo

import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual class GPSSensorHelper(
    val appContext: Context,
    val gpsLifecycleObserver: GPSLifecycleObserver
) {
    private val locationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    actual suspend fun isGPSEnabled(): Boolean = suspendCancellableCoroutine { continuation ->
        // check if GPS is enabled
        val isLocationEnabled = LocationManagerCompat.isLocationEnabled(
            appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        )

        if (!isLocationEnabled) {
            // GPS is not enabled

            // ask the user to turn GPS on
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

            val client: SettingsClient = LocationServices.getSettingsClient(appContext)
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

            task.addOnSuccessListener {
                // GPS is enabled
                continuation.resume(true)
            }

            task.addOnFailureListener { exception ->
                // GPS is not enabled
                if (exception is ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // send an intent and receive the activity result via the custom observer
                        gpsLifecycleObserver.resolveException(exception = exception) { isGPSenabled ->
                            if(isGPSenabled) {
                                continuation.resume(true)
                            } else {
                                continuation.resume(false)
                            }
                        }
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                        continuation.resume(false)
                    }
                } else {
                    // unknown exception
                    continuation.resume(false)
                }
            }
        } else {
            // GPS is already enabled
            continuation.resume(true)
        }
    }
}