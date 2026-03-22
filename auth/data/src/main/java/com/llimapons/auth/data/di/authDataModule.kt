package com.llimapons.auth.data.di

import com.llimapons.auth.data.AuthRepositoryImpl
import com.llimapons.auth.data.EmailPatternValidator
import com.llimapons.auth.domain.AuthRepository
import com.llimapons.auth.domain.PatternValidator
import com.llimapons.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}