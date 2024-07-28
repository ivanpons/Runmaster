package com.llimapons.presentation.run_active.maps

import androidx.compose.ui.graphics.Color
import com.llimapons.core.domain.location.Location

data class PolylineUi(
    val location1: Location,
    val location2: Location,
    val color: Color,

)
