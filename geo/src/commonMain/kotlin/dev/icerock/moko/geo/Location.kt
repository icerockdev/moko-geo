package dev.icerock.moko.geo

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
class Location(
    val coordinates: LatLng,
    val coordinatesAccuracyMeters: Double
) : Parcelable
