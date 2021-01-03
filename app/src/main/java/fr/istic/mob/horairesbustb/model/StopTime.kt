package fr.istic.mob.horairesbustb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="stop_times")
data class StopTime (
    @PrimaryKey(autoGenerate = true)
    val id :Int,
    @ColumnInfo(name = "trip_id")
    val tripId : String?,
    @ColumnInfo(name = "arrival_time")
    val arrival : String?,
    @ColumnInfo(name = "departure_time")
    val departure : String?,
    @ColumnInfo(name = "stop_id")
    val stopId : String?,
    @ColumnInfo(name = "stop_sequence")
    val sequence : String?,
    @ColumnInfo(name = "stop_headsign")
    val headSign : String?,
    @ColumnInfo(name = "pickup_type")
    val pickupType : String?,
    @ColumnInfo(name = "drop_off_type")
    val dropOffType : String?,
    @ColumnInfo(name = "shape_dist_traveled")
    val shapeDistTraveled : String?
)