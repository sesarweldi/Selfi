package com.selfi.models

import com.google.gson.annotations.SerializedName

data class JadwalMapel (
    val id_mapel: Int,
    val nama_mapel: String,
    val id_kelas: Int,
    @SerializedName("nama")
    val kelas: String
)