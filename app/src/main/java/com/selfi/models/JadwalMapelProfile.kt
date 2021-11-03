package com.selfi.models

data class JadwalMapelProfile(
    val id: Int,
    val hari: String,
    val waktu_dimulai: String,
    val waktu_selesai: String,
    val id_mapel: Int,
    val id_kelas: Int,
    val mapel: mapel1
) {
    data class mapel1(
        val id_mapel: Int,
        val nama_mapel: String
    )
}