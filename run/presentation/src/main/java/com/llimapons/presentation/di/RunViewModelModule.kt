package com.llimapons.presentation.di

import com.llimapons.presentation.run_active.ActiveRunViewModel
import com.llimapons.presentation.run_overview.RunOverviewViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val runViewModelModule = module {
    singleOf(::RunOverviewViewModel)
    singleOf(::ActiveRunViewModel)
}