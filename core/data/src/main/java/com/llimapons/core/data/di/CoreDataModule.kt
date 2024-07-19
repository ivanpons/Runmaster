package com.llimapons.core.data.di

import com.llimapons.core.data.networking.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single{
        HttpClientFactory().build()
    }
}