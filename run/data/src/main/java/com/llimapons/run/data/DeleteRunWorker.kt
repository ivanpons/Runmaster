package com.llimapons.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.llimapons.core.database.dao.RunPendingSyncDao
import com.llimapons.core.domain.run.RemoteRunDataSource

class DeleteRunWorker(
    context: Context,
    private val params: WorkerParameters,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val pendingSyncDao: RunPendingSyncDao
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }

        val runId = params.inputData.getString(RUN_ID) ?: return Result.failure()
        return when (val result = remoteRunDataSource.deleteRun(runId)) {
            is com.llimapons.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }

            is com.llimapons.core.domain.util.Result.Success -> {
                pendingSyncDao.deleteRunPendingSyncEntity(runId)
                Result.success()
            }
        }
    }

    companion object {
        const val RUN_ID = "run_id"
    }

}