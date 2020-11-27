package com.example.horairesbustb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="stop_times")
data class StopTime (
    @PrimaryKey(autoGenerate = true)
    val id :Int,
    @ColumnInfo(name = "trip_id")
    val TRIP_ID : String?,
    @ColumnInfo(name = "arrival_time")
    val ARRIVAL_TIME : String?,
    @ColumnInfo(name = "departure_time")
    val DEPARTURE_TIME : String?,
    @ColumnInfo(name = "stop_id")
    val STOP_ID : String?,
    @ColumnInfo(name = "stop_sequence")
    val STOP_SEQUENCE : String?
)