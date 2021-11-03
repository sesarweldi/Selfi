package com.selfi.models

import com.google.gson.annotations.SerializedName

data class Kelas (
    val id_kelas: Int,
    @SerializedName("nama")
    val nama_kelas: String
)