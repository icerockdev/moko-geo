package dev.icerock.moko.geo

expect class GPSSensorHelper {
    suspend fun isGPSEnabled(): Boolean
}