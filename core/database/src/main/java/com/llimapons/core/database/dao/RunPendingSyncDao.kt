package com.llimapons.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.llimapons.core.database.entity.DeleteRunSyncEntity
import com.llimapons.core.database.entity.RunPendingSyncEntity

@Dao
interface RunPendingSyncDao {

    @Query("SELECT * FROM runpendingsyncentity WHERE userId = :userId")
    suspend fun getAllRunPendingSyncEntities(userId: String): List<RunPendingSyncEntity>

    @Query("SELECT * FROM runpendingsyncentity WHERE runId = :runId")
    suspend fun getRunPendingSyncEntity(runId: String): RunPendingSyncEntity?

    @Upsert
    suspend fun upsertRunPendingSyncEntity(runPendingSyncEntity: RunPendingSyncEntity)

    @Query("DELETE FROM runpendingsyncentity WHERE runId = :runId")
    suspend fun deleteRunPendingSyncEntity(runId: String)



    @Query("SELECT * FROM deleterunsyncentity WHERE userId = :userId")
    suspend fun getAllDeleteRunSyncEntities(userId: String): List<DeleteRunSyncEntity>

    @Upsert
    suspend fun upsertDeleteRunSyncEntity(deleteRunSyncEntity: DeleteRunSyncEntity)

    @Query("DELETE FROM deleterunsyncentity WHERE runId = :runId")
    suspend fun deleteDeleteRunSyncEntity(runId: String)
}