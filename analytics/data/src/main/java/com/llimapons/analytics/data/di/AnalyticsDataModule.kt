package com.llimapons.analytics.data.di

import com.llimapons.analytics.data.RoomAnalyticsRepository
import com.llimapons.analytics.domain.AnalyticsRepository
import com.llimapons.core.database.RunDataBase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsDataModule = module {
    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()

    single { get<RunDataBase>().analyticsDao}
}