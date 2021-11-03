package com.selfi.services.api

import com.selfi.models.JadwalHari
import com.selfi.models.JadwalMapelProfile
import com.selfi.models.response.DataResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface JadwalService {


    @GET("/selfi/jadwal/{id_kelas}")
    fun getJadwalByKelas(
        @Path("id_kelas") id_kelas: Int
    ): Call<DataResponseModel<List<JadwalHari>>>

    @GET("/selfi/jadwal/{id_kelas}")
    fun getJadwalByKelasMapel(
        @Path("id_kelas") id_kelas: Int
    ): Call<DataResponseModel<List<JadwalHari.JadwalMapel>>>

    @GET("selfi/jadwal/{id_kelas}/{hari}")
    fun getJadwalByHari(
        @Path("id_kelas") id_kelas: Int,
        @Path("hari") hari: String
    ): Call<DataResponseModel<List<JadwalMapelProfile>>>
}