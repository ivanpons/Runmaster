package com.llimapons.core.domain.run

import com.llimapons.core.domain.location.Location
import java.time.ZonedDateTime
import kotlin.time.Duration
import kotlin.time.DurationUnit

data class Run(
    val id: String?,
    val duration: Duration,
    val dateTimeUtc: ZonedDateTime,
    val distanceMeters: Int,
    val location: Location,
    val maxSpeedHmh: Double,
    val totalElevationMeters: Int,
    val mapPictureUrl: String?
    ){

    val avgSpeedKmh: Double
        get() = (distanceMeters / 1000.0) /duration.toDouble(DurationUnit.HOURS)
}
