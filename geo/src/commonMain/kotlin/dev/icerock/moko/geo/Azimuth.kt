package dev.icerock.moko.geo

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class Azimuth(
    val azimuthDegrees: Double,
    val azimuthAccuracyDegrees: Double
) : Parcelable
