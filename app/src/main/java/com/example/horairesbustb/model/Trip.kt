package com.example.horairesbustb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="trips")
data class Trip (
    @PrimaryKey(autoGenerate = true)
    val id :Int,
    @ColumnInfo(name = "route_id")
    val ROUTE_ID : String?,
    @ColumnInfo(name = "service_id")
    val SERVICE_ID : String?,
    @ColumnInfo(name = "trip_headsign")
    val HEADSIGN : String?,
    @ColumnInfo(name = "direction_id")
    val DIRECTION_ID : String?,
    @ColumnInfo(name = "block_id")
    val BLOCK_ID : String?,
    @ColumnInfo(name = "wheelchair_accessible")
    val WHEELCHAIR_ACCESSIBLE : String?
)