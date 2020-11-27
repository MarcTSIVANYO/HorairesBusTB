package com.example.horairesbustb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="route_details")
data class RouteDetail (
    @PrimaryKey(autoGenerate = true)
    val id :Int
)