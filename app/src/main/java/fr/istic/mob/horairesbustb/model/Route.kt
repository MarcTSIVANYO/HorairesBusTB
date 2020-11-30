package fr.istic.mob.horairesbustb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="routes")
data class Route (
    @PrimaryKey()
    @ColumnInfo(name = "route_id")
    val id :Int,
    @ColumnInfo(name = "agency_id")
    val serviceId :Int,
    @ColumnInfo(name = "route_short_name")
    val shortName : String?,
    @ColumnInfo(name = "route_long_name")
    val longName : String?,
    @ColumnInfo(name = "route_desc")
    val description : String?,
    @ColumnInfo(name = "route_type")
    val type : String?,
    @ColumnInfo(name = "route_url")
    val url : String?,
    @ColumnInfo(name = "route_color")
    val color : String?,
    @ColumnInfo(name = "route_text_color")
    val textColor : String?,
    @ColumnInfo(name = "route_sort_order")
    val sortOrder : String?
)