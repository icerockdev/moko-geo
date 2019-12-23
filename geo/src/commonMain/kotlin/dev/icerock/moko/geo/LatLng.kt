/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.geo

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import kotlin.math.*

@Parcelize
data class LatLng(
    val latitude: Double, 
    val longitude: Double
) : Parcelable {

    fun distanceTo(latLng: LatLng): Double {
        val lat1 = latitude
        val lon1 = longitude
        val lat2 = latLng.latitude
        val lon2 = latLng.longitude

        val r = EARTH_RADIUS
        val dLat = toRadians(lat2 - lat1)
        val dLon = toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(toRadians(lat1)) * cos(toRadians(lat2)) * sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * asin(sqrt(a))
        return r * c
    }

    fun getAngleTo(latLng: LatLng, rounded: Boolean = true): Double {
        val lat1 = toRadians(this.latitude)
        val lat2 = toRadians(latLng.latitude)
        val lon1 = toRadians(this.longitude)
        val lon2 = toRadians(latLng.longitude)

        val dLon = lon2 - lon1

        val y = sin(dLon) * cos(lat2)
        val x = cos(lat1) * sin(lat2) -
                (sin(lat1) * cos(lat2) * cos(dLon))

        var brng = atan2(y, x)

        brng = toDegree(brng)
        brng = (brng + 360) % 360

        var angle = brng
        if (rounded) {
            angle = (round(brng / 10) * 10)
        }
        if (angle == 360.0) angle = 0.0

        return angle
    }

    private fun toRadians(angle: Double): Double {
        return angle * PI / 180.0
    }

    private fun toDegree(angle: Double): Double {
        return angle * 180.0 / PI
    }

    private companion object {
        const val EARTH_RADIUS = 6371.00 // Radius in Kilometers default
    }
}
