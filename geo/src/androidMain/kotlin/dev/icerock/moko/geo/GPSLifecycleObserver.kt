package dev.icerock.moko.geo

import android.app.Activity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.common.api.ResolvableApiException

class GPSLifecycleObserver(private val registry : ActivityResultRegistry): DefaultLifecycleObserver {
    lateinit var resultLauncher : ActivityResultLauncher<IntentSenderRequest>
    private var callback: (Boolean) -> Unit = {}

    override fun onCreate(owner: LifecycleOwner) {
        // register launcher that reacts to onActivityResult()
        resultLauncher = registry.register("key", owner, ActivityResultContracts.StartIntentSenderForResult()) { result ->
            // check the result code
            if (result.resultCode == Activity.RESULT_OK) {
                // user accepted, GPS was turned on
                callback(true)
            } else {
                // user didn't accept, GPS was not turned on
                callback(false)
            }

            callback = {}
        }
    }

    fun resolveException(exception: ResolvableApiException, onResult: (Boolean) -> Unit) {
        callback = onResult

        // create intent from exception
        val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()

        // perform the actual request
        resultLauncher.launch(intentSenderRequest)
    }
}