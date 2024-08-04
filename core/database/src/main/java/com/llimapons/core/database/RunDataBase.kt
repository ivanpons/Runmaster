package com.llimapons.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.llimapons.core.database.dao.RunDao
import com.llimapons.core.database.dao.RunPendingSyncDao
import com.llimapons.core.database.entity.DeleteRunSyncEntity
import com.llimapons.core.database.entity.RunEntity
import com.llimapons.core.database.entity.RunPendingSyncEntity

@Database(
    entities = [
        RunEntity::class,
        RunPendingSyncEntity::class,
        DeleteRunSyncEntity::class
    ],
    version = 1
)
abstract class RunDataBase : RoomDatabase() {

    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao

}