package com.llimapons.presentation.di

import com.llimapons.presentation.run_active.ActiveRunViewModel
import com.llimapons.presentation.run_overview.RunOverviewViewModel
import com.llimapons.run.domain.RunningTracker
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val runPresentationModule = module {
    singleOf(::RunningTracker)

    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}