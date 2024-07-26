package com.llimapons.presentation.run_overview

sealed interface RunOverviewAction {
    data object OnStartClick : RunOverviewAction
    data object OnAnalyticsClick : RunOverviewAction
    data object OnLogoutClick : RunOverviewAction
}