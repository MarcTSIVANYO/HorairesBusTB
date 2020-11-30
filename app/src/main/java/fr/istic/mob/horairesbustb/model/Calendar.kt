package fr.istic.mob.horairesbustb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="calendars")
data class Calendar (
    @PrimaryKey()
    @ColumnInfo(name = "service_id")
    val serviceId :Int,
    val monday  : String?,
    val thursday  : String?,
    val wednesday  : String?,
    val friday  : String?,
    val saturday  : String?,
    val sunday  : String?,
    @ColumnInfo(name = "start_date")
    val startDate  : String?,
    @ColumnInfo(name = "end_date")
    val endDate  : String?
)