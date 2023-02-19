package com.sanmer.geomag.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }

            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "geomag")
                .build().apply {
                    instance = this
                }
        }
    }
}