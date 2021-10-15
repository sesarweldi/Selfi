package com.selfi.models

import com.google.gson.annotations.SerializedName


data class User(
    val nis: Int,
    val nama: String?,
    @SerializedName("id_kelas")
    val Idkelas:Int,
    val jurusan: String?,
    val nohp: String?,
    val password: String?
)