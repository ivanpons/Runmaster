package com.llimapons.analytics.presentation

sealed interface AnalyticsAction {
    data object OnBackClick : AnalyticsAction
}