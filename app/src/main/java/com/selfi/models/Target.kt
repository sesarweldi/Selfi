package com.selfi.models

import com.google.gson.annotations.SerializedName


data class Target (
    @SerializedName("id_target")
    val idTarget: Int,
    val nis: Int,
    @SerializedName("judul_target")
    val judulTarget: String,
    @SerializedName("deskripsi_target")
    val deskripsiTarget: String
)