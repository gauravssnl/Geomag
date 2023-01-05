package com.sanmer.geomag.data

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.sanmer.geomag.core.models.MagneticField
import com.sanmer.geomag.core.time.DateTime
import com.sanmer.geomag.data.database.AppDatabase
import com.sanmer.geomag.data.database.toEntity
import com.sanmer.geomag.data.database.toRecord
import com.sanmer.geomag.data.record.Position
import com.sanmer.geomag.data.record.Record
import kotlinx.coroutines.*

object Constant {
    private val job = Job()
    private val coroutineScope = CoroutineScope(job)
    private lateinit var db: AppDatabase
    private val recordDao get() = db.recordDao()

    val records = mutableStateListOf<Record>()

    fun init(context: Context): AppDatabase {
        db = AppDatabase.getDatabase(context)
        getAll()

        return db
    }

    fun getAll() {
        coroutineScope.launch(Dispatchers.IO) {
            val list = withContext(Dispatchers.IO) {
                recordDao.getAll()
            }
            records.clear()
            records.addAll(list.map { it.toRecord() })
        }
    }

    suspend fun insert(value: Record) = withContext(Dispatchers.IO) {
        records.add(value)
        recordDao.insert(value.toEntity())
    }

    suspend fun insert(list: List<Record>) = withContext(Dispatchers.IO) {
        records.clear()
        records.addAll(list)
        recordDao.insert(list.map { it.toEntity() })
    }

    suspend fun delete(list: List<Record>) = withContext(Dispatchers.IO) {
        records.removeAll(list)
        recordDao.delete(list.map { it.toEntity() })
    }

    fun delete(value: Record) {
        coroutineScope.launch(Dispatchers.IO) {
            records.remove(value)
            recordDao.delete(value.toEntity())
        }
    }

    fun deleteAll() {
        coroutineScope.launch(Dispatchers.IO) {
            records.clear()
            recordDao.deleteAll()
        }
    }
}