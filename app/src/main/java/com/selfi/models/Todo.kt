package com.selfi.models

import com.google.gson.annotations.SerializedName

data class Todo(
    @SerializedName("id_kegiatan")
    val id: Int,
    @SerializedName("nama_kegiatan")
    val kegiatan: String,
    val nis: Int,
    val tanggal: String,
    val jam: String,
    val status: String
)