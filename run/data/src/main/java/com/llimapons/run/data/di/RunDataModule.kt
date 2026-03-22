package com.llimapons.run.data.di

import com.llimapons.core.domain.run.SyncRunScheduler
import com.llimapons.run.data.CreateRunWorker
import com.llimapons.run.data.DeleteRunWorker
import com.llimapons.run.data.FetchRunsWorker
import com.llimapons.run.data.SyncRunWorkerScheduler
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::DeleteRunWorker)
    workerOf(::FetchRunsWorker)

    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
}