package com.selfi.services.api

import com.selfi.models.Target
import com.selfi.models.response.DataResponseModel
import com.selfi.models.response.ResponseDB
import retrofit2.Call
import retrofit2.http.*

interface TargetService {

    @FormUrlEncoded
    @POST("selfi/target/tambah")
    fun addTarget(
        @Field("id_target") id_target: Int,
        @Field("nis") nis: Int,
        @Field("judul_target") judul_target: String,
        @Field("deskripsi_target") deskripsi_target: String
    ) : Call<ResponseDB>

    @GET("/selfi/target/{nis}")
    fun getTarget(
        @Path("nis") nis: Int
    ) : Call <DataResponseModel<List<Target>>>

    @DELETE("/selfi/target/{nis}/{id_target}")
    fun deleteTarget(
        @Path("nis") nis: Int,
        @Path("id_target") id_target: Int
    ) : Call <ResponseDB>
}

