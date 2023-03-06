package com.sanmer.geomag.data

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.sanmer.geomag.data.database.AppDatabase
import com.sanmer.geomag.data.database.toEntity
import com.sanmer.geomag.data.database.toRecord
import com.sanmer.geomag.data.record.Record
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object Constant {
    private lateinit var db: AppDatabase
    private val recordDao get() = db.recordDao()

    fun init(context: Context): AppDatabase {
        db = AppDatabase.getDatabase(context)
        return db
    }

    suspend fun getAll() = withContext(Dispatchers.IO) {
        recordDao.getAll().asReversed().map { it.toRecord() }
    }

    suspend fun insert(value: Record) = withContext(Dispatchers.IO) {
        recordDao.insert(value.toEntity())
    }

    suspend fun insert(list: List<Record>) = withContext(Dispatchers.IO) {
        recordDao.insert(list.map { it.toEntity() })
    }

    suspend fun delete(list: List<Record>) = withContext(Dispatchers.IO) {
        recordDao.delete(list.map { it.toEntity() })
    }

    suspend fun delete(value: Record) = withContext(Dispatchers.IO) {
        recordDao.delete(value.toEntity())
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        recordDao.deleteAll()
    }
}