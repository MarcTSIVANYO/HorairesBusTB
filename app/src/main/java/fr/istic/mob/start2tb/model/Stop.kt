package fr.istic.mob.horairesbustb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="stops")
data class Stop(
    @PrimaryKey
    @ColumnInfo(name = "stop_id")
    val id: String?,
    @ColumnInfo(name = "stop_name")
    val name: String?,
    @ColumnInfo(name = "stop_desc")
    val description: String?,
    @ColumnInfo(name = "stop_lat")
    val latitude: String?,
    @ColumnInfo(name = "stop_lon")
    val longitude: String?
)