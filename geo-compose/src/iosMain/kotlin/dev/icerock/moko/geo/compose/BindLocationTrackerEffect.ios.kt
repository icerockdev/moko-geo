/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.geo.compose

import androidx.compose.runtime.Composable
import dev.icerock.moko.geo.LocationTracker

// on iOS side we should not do anything to prepare LocationTracker to work
@Suppress("FunctionNaming")
@Composable
actual fun BindLocationTrackerEffect(locationTracker: LocationTracker) = Unit
