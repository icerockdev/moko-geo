package dev.icerock.moko.geo

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class Speed(
    val speedMps: Double,
    val speedAccuracyMps: Double
) : Parcelable
