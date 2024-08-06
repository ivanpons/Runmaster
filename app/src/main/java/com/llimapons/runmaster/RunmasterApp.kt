package com.llimapons.runmaster

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat
import com.llimapons.auth.data.di.authDataModule
import com.llimapons.auth.presentation.di.authViewModelModule
import com.llimapons.core.data.di.coreDataModule
import com.llimapons.core.database.di.dataBaseModule
import com.llimapons.presentation.di.runPresentationModule
import com.llimapons.run.data.di.runDataModule
import com.llimapons.run.di.networkModule
import com.llimapons.run.location.di.locationModule
import com.llimapons.runmaster.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class RunmasterApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RunmasterApp)
            workManagerFactory()
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                runPresentationModule,
                locationModule,
                dataBaseModule,
                networkModule,
                runDataModule
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}