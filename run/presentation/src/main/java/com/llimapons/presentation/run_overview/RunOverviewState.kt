package com.llimapons.presentation.run_overview

import com.llimapons.presentation.run_overview.model.RunUi

data class RunOverviewState(
    val runs: List<RunUi> = emptyList()
)
