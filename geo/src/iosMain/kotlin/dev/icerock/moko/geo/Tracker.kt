/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.geo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.darwin.NSObject

internal expect class Tracker(
    locationsChannel: Channel<LatLng>,
    extendedLocationsChannel: Channel<ExtendedLocation>,
    scope: CoroutineScope
) : NSObject, CLLocationManagerDelegateProtocol
