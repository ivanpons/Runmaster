package com.llimapons.runmaster

import android.app.Application
import com.llimapons.auth.data.di.authDataModule
import com.llimapons.auth.presentation.di.authViewModelModule
import com.llimapons.core.data.di.coreDataModule
import com.llimapons.runmaster.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RunmasterApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RunmasterApp)
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
            )
        }
    }
}