package com.selfi.services.api

import com.selfi.models.Motivasi
import com.selfi.models.Target
import com.selfi.models.response.DataResponseModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MotivasiService {

    @GET("/selfi/motivasi/")
    fun getMotivasi(
    ) : Call<DataResponseModel<List<Motivasi>>>


    @GET("selfi/motivasi/search}")
    fun searchMotivasi(
        @Query("keyword") key: String?
    ) : Call<DataResponseModel<List<Motivasi>>>



}