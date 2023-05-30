package com.sanmer.geomag.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sanmer.geomag.database.dao.RecordDao
import com.sanmer.geomag.database.entity.RecordEntity

@Database(entities = [RecordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
}