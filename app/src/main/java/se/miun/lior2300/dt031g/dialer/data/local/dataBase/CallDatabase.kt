package se.miun.lior2300.dt031g.dialer.data.local.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import se.miun.lior2300.dt031g.dialer.data.local.dao.CallDao
import se.miun.lior2300.dt031g.dialer.data.local.entity.Call

//https://200oksolutions.com/blog/exploring-android-room-database-with-kotlin/
@Database(
    entities = [Call::class],
    version = 2,
    exportSchema = false
)
abstract class CallDatabase : RoomDatabase() {

    abstract fun callDao(): CallDao

    companion object {
        @Volatile
        private var INSTANCE: CallDatabase? = null

        fun getInstance(context: Context): CallDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CallDatabase::class.java,
                    "call_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}