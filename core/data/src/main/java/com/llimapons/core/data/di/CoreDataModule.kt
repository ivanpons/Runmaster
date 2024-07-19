package com.llimapons.core.data.di

import com.llimapons.core.data.auth.EncryptedSessionStorage
import com.llimapons.core.data.networking.HttpClientFactory
import com.llimapons.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single{
        HttpClientFactory(get()).build()
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}