package com.sanmer.geomag.data

import android.content.Context
import com.sanmer.geomag.data.database.AppDatabase
import com.sanmer.geomag.data.database.toEntity
import com.sanmer.geomag.data.database.toRecord
import com.sanmer.geomag.data.record.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

object Constant {
    private lateinit var db: AppDatabase
    private val recordDao get() = db.recordDao()

    fun init(context: Context) {
        db = AppDatabase.getDatabase(context)
    }

    fun getAllAsFlow() = recordDao.getAllAsFlow().map { list ->
        list.map { it.toRecord() }.asReversed()
    }

    suspend fun getAll() = withContext(Dispatchers.IO) {
        recordDao.getAll().map { it.toRecord() }.asReversed()
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