package fr.istic.mob.horairesbustb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="trips")
data class Trip (
    @ColumnInfo(name = "route_id")
    val routeId : String?,
    @ColumnInfo(name = "service_id")
    val serviceId : String?,
    @PrimaryKey()
    @ColumnInfo(name = "trip_id")
    val id :String,
    @ColumnInfo(name = "trip_headsign")
    val headSign : String?,
    @ColumnInfo(name = "trip_short_name")
    val shortName : String?,
    @ColumnInfo(name = "direction_id")
    val directionId : String?,
    @ColumnInfo(name = "block_id")
    val blockId : String?,
    @ColumnInfo(name = "shape_id")
    val shapeId : String?,
    @ColumnInfo(name = "wheelchair_accessible")
    val WHEELCHAIR_ACCESSIBLE : String?,
    @ColumnInfo(name = "bikes_allowed")
    val bikesAllowed : String?
)