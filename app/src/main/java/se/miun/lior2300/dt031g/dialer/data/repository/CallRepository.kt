package se.miun.lior2300.dt031g.dialer.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import se.miun.lior2300.dt031g.dialer.data.local.dao.CallDao
import se.miun.lior2300.dt031g.dialer.data.local.dataBase.CallDatabase
import se.miun.lior2300.dt031g.dialer.data.local.entity.Call

class CallRepository(context: Context) {

    private val callDao: CallDao

    val allCalls: LiveData<List<Call>>

    init {
        val db = CallDatabase.getInstance(context)
        callDao = db.callDao()
        allCalls = callDao.getAllCalls()
    }

    suspend fun insert(call: Call) {
        callDao.insert(call)
    }

    suspend fun deleteAll() {
        callDao.deleteAll()
    }

    val callsWithLocation: LiveData<List<Call>> = callDao.getAllCalls()
}