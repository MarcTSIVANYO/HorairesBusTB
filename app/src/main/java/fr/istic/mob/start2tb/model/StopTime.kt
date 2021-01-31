package fr.istic.mob.horairesbustb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="stop_times")
data class StopTime(
    @ColumnInfo(name = "trip_id")
    val tripId: String?,
    @ColumnInfo(name = "arrival_time")
    val arrival: String?,
    @ColumnInfo(name = "departure_time")
    val departure: String?,
    @ColumnInfo(name = "stop_id")
    val stopId: String?,
    @ColumnInfo(name = "stop_sequence")
    val sequence: String?,

){
    var stopName: String? = null
    fun setNomArret(stopName : String){
        this.stopName = stopName
    }
}