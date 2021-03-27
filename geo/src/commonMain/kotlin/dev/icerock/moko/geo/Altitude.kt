package dev.icerock.moko.geo

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class Altitude(
    val altitudeMeters: Double,
    val altitudeAccuracyMeters: Double
) : Parcelable
