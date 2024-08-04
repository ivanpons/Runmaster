package com.llimapons.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.llimapons.core.database.dao.RunDao
import com.llimapons.core.database.entity.RunEntity

@Database(
    entities = [RunEntity::class],
    version = 1
)
abstract class RunDataBase: RoomDatabase() {

    abstract val runDao: RunDao

}