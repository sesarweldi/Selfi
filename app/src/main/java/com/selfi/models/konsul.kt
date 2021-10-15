package com.selfi.models

import com.google.gson.annotations.SerializedName

data class konsul (
    @SerializedName("id_konseling")
    val idKonseling: Int,
    val nama: String,
    val keahlian: String,
    val nohp: String
)