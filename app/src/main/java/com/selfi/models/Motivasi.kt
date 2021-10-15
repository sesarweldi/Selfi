package com.selfi.models

import com.google.gson.annotations.SerializedName

data class Motivasi (
    @SerializedName("id_motivasi")
    val motivasiId: Int,
    val judul: String,
    val deskripsi: String,
    val sampul: String
)