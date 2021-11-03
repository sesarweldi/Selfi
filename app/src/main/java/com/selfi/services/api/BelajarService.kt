package com.selfi.services.api

import com.selfi.models.Belajar
import com.selfi.models.response.DataResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BelajarService {

    @GET("selfi/belajar/normatif/search")
    fun searchBukuNormatif(
        @Query("keyword") keyword: String?
    )
            : Call<DataResponseModel<List<Belajar>>>

    @GET("selfi/belajar/produktif/search")
    fun searchBukuProduktif(
        @Query("keyword") keyword: String?
    )
            : Call<DataResponseModel<List<Belajar>>>
}