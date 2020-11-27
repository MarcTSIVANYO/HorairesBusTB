package com.example.horairesbustb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="stops")
data class Stop (
    @PrimaryKey(autoGenerate = true)
    val id :Int,
    @ColumnInfo(name = "stop_name")
    val ROUTE_ID : String?,
    @ColumnInfo(name = "stop_desc")
    val DESCRIPTION  : String?,
    @ColumnInfo(name = "stop_lat")
    val LATITUDE  : String?,
    @ColumnInfo(name = "stop_lon")
    val LONGITUDE  : String?,
    @ColumnInfo(name = "wheelchair_accessible")
    val WHEELCHAIR_ACCESSIBLE : String?
)