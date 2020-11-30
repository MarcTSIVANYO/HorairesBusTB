package fr.istic.mob.horairesbustb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="stops")
data class Stop (
    @PrimaryKey
    @ColumnInfo(name = "stop_id")
    val id :Int,
    @ColumnInfo(name = "stop_code")
    val code : String?,
    @ColumnInfo(name = "stop_name")
    val name : String?,
    @ColumnInfo(name = "stop_desc")
    val description : String?,
    @ColumnInfo(name = "stop_lat")
    val latitude  : String?,
    @ColumnInfo(name = "stop_lon")
    val longitude  : String?,
    @ColumnInfo(name = "zone_id")
    val zoneId  : String?,
    @ColumnInfo(name = "stop_url")
    val stopUrl  : String?,
    @ColumnInfo(name = "location_type")
    val locationType  : String?,
    @ColumnInfo(name = "parent_station")
    val parentStation  : String?,
    @ColumnInfo(name = "stop_timezone")
    val timeZone  : String?,
    @ColumnInfo(name = "wheelchair_accessible")
    val wheelchairAccessible : String?
)