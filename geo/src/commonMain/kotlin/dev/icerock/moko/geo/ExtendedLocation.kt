package dev.icerock.moko.geo

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class ExtendedLocation(
    val location: Location,
    val azimuth: Azimuth,
    val speed: Speed,
    val altitude: Altitude,
    val timestampMs: Long
) : Parcelable
