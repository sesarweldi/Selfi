package com.selfi.models

import com.google.gson.annotations.SerializedName

data class JadwalHari(
    val hari: String,
    @SerializedName("kela")
    val kelas: Kelas,
    @SerializedName("mapels")
    val mapel: List<JadwalMapel>
){
    data class JadwalMapel (
        val id_mapel: Int,
        val waktu_dimulai: String,
        val waktu_selesai: String,
        @SerializedName("mapel.nama_mapel")
        val nama_mapel: String)
}