package com.selfi.services.api

import com.selfi.models.JadwalMapel
import com.selfi.models.response.DataResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface JadwalService {


    @GET("/selfi/jadwal/{id_kelas}/{hari}")
    fun getJadwalByKelasByHari(
        @Path("id_kelas") id_kelas: Int,
        @Path("hari") hari: String
    ): Call<DataResponseModel<List<JadwalMapel>>>
}