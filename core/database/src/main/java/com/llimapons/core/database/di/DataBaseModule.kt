package com.llimapons.core.database.di

import androidx.room.Room
import com.llimapons.core.database.RoomLocalDataSource
import com.llimapons.core.database.RunDataBase
import com.llimapons.core.domain.run.LocalRunDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataBaseModule = module {
    single{
        Room.databaseBuilder(
            androidApplication(),
            RunDataBase::class.java,
            "run_db"
        ).build()
    }

    single {get<RunDataBase>().runDao}

    singleOf(::RoomLocalDataSource).bind<LocalRunDataSource>()
}