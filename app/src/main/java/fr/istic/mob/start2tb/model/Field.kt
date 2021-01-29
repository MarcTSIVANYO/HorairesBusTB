package fr.istic.mob.horairesbustb.model

import com.google.gson.annotations.SerializedName
data class Field (
    @SerializedName("url")
    var url:String = "",
    @SerializedName("debutvalidite")
    var dateDebut:String = "",
    @SerializedName("finvalidite")
    var dateFin:String = ""
)