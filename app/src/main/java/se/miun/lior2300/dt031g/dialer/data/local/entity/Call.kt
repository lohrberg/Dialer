package se.miun.lior2300.dt031g.dialer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("calls")
data class Call (

    @PrimaryKey(true)
    val id: Int = 0,
    val number: String,
    val timestamp: Long,

    val latitude: Double?,
    val longitude: Double?

)