package com.llimapons.run.location

import android.location.Location
import com.llimapons.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
      location = com.llimapons.core.domain.location.Location(
          lat = latitude,
          long = longitude
      ),
        altitude = altitude
    )
}