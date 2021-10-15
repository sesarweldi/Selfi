package com.selfi.services.api

import com.selfi.models.Belajar
import com.selfi.models.response.DataResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface BelajarService {

    @GET("selfi/belajar/normatif")
    fun getAllBukuNormatif()
    : Call<DataResponseModel<List<Belajar>>>

    @GET("selfi/belajar/produktif")
    fun getAllBukuProduktif()
            : Call<DataResponseModel<List<Belajar>>>
}