package com.example.horairesbustb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="routes")
data class Route (
    @PrimaryKey(autoGenerate = true)
    val id :Int,
    @ColumnInfo(name = "route_short_name")
    val SHORT_NAME : String?,
    @ColumnInfo(name = "route_long_name")
    val LONG_NAME : String?,
    @ColumnInfo(name = "route_desc") val DESCRIPTION : String?,
    @ColumnInfo(name = "route_type") val TYPE : String?,
    @ColumnInfo(name = "route_color") val COLOR : String?,
    @ColumnInfo(name = "route_text_color") val TEXT_COLOR : String?
)