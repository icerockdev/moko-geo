/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.geo.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import dev.icerock.moko.geo.LocationTracker

@Suppress("FunctionNaming")
@Composable
actual fun BindLocationTrackerEffect(locationTracker: LocationTracker) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val context: Context = LocalContext.current

    LaunchedEffect(locationTracker, lifecycleOwner, context) {
        val fragmentManager: FragmentManager = (context as FragmentActivity).supportFragmentManager

        locationTracker.bind(lifecycleOwner.lifecycle, context, fragmentManager)
    }
}
