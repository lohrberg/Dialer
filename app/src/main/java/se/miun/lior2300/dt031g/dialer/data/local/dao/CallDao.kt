package se.miun.lior2300.dt031g.dialer.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import se.miun.lior2300.dt031g.dialer.data.local.entity.Call

@Dao
interface CallDao {
    @Query("SELECT * FROM calls ORDER BY timestamp DESC")
    fun getAllCalls(): LiveData<List<Call>>

    @Insert
    suspend fun insert(call: Call)

    @Query("DELETE FROM calls")
    suspend fun deleteAll()

    @Query("SELECT * FROM calls WHERE latitude IS NOT NULL AND longitude IS NOT NULL")
    fun getAllCallsWithLocation(): LiveData<List<Call>>
}