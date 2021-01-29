package fr.istic.mob.horairesbustb.model

import com.google.gson.annotations.SerializedName

data class Version (
    @SerializedName("recordid")
    var recordId:String = "",
    @SerializedName("fields")
    var field: Field
)