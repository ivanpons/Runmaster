package com.llimapons.core.data.di

import com.llimapons.core.data.auth.EncryptedSessionStorage
import com.llimapons.core.data.networking.HttpClientFactory
import com.llimapons.core.data.run.OfflineFirstRunRepository
import com.llimapons.core.domain.SessionStorage
import com.llimapons.core.domain.run.RunRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

val coreDataModule = module {
    single{
        HttpClientFactory(get()).build()
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()
}