package com.selfi.services.api

import com.selfi.models.konsul
import com.selfi.models.response.DataResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface KonsulService {

    @GET("/selfi/konseling")
    fun getKonseling(
    ): Call <DataResponseModel<List<konsul>>>
}