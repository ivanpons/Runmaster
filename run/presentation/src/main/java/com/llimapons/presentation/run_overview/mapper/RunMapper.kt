package com.llimapons.presentation.run_overview.mapper

import com.llimapons.core.domain.run.Run
import com.llimapons.core.presentation.ui.formatted
import com.llimapons.core.presentation.ui.toFormattedKm
import com.llimapons.core.presentation.ui.toFormattedKmh
import com.llimapons.core.presentation.ui.toFormattedMeters
import com.llimapons.core.presentation.ui.toFormattedPace
import com.llimapons.presentation.run_overview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Run.toRunUi(): RunUi {
    val dateTimeInLocalTime = dateTimeUtc
        .withZoneSameInstant(ZoneId.systemDefault())
    val formattedDateTime = DateTimeFormatter
        .ofPattern("MMM dd, yyyy - hh:mma")
        .format(dateTimeInLocalTime)

    val distanceKm = distanceMeters / 1000.0

    return RunUi(
        id = id!!,
        duration = duration.formatted(),
        dateTime = formattedDateTime,
        distance = distanceKm.toFormattedKm(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedHmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl
    )
}