package com.example.horairesbustb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="calendars")
data class Calendar (
    @PrimaryKey()
    val service_id :Int,
    @ColumnInfo(name = "monday")
    val MONDAY  : String?,
    @ColumnInfo(name = "thursday")
    val THURSDAY  : String?,
    @ColumnInfo(name = "wednesday")
    val WEDNESDAY  : String?,
    @ColumnInfo(name = "friday")
    val FRIDAY  : String?,
    @ColumnInfo(name = "saturday")
    val SATURDAY  : String?,
    @ColumnInfo(name = "sunday")
    val SUNDAY  : String?,
    @ColumnInfo(name = "start_date")
    val START_DATE  : String?,
    @ColumnInfo(name = "end_date")
    val END_DATE  : String?
)