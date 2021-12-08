package com.selfi.services.api

import com.selfi.models.Todo
import com.selfi.models.response.DataResponseModel
import com.selfi.models.response.ResponseDB
import retrofit2.Call
import retrofit2.http.*
import java.time.LocalDateTime

interface TodoService {

    @FormUrlEncoded
    @POST("selfi/todolist/tambah")
    fun addTodo(
        @Field("id_kegiatan") id_todo: Int,
        @Field("nis") nis: Int,
        @Field("nama_kegiatan") nama_kegiatan: String,
        @Field("tanggal") tanggal_todo: LocalDateTime
    ) : Call<ResponseDB>

    @GET("selfi/todolist/{nis}/progress")
    fun getTodoUncompleted(
        @Path("nis") nis: Int
    ) : Call<DataResponseModel<List<Todo>>>

    @GET("selfi/todolist/{nis}/completed")
    fun getTodoCompleted(
        @Path("nis") nis: Int
    ) : Call<DataResponseModel<List<Todo>>>

    @FormUrlEncoded
    @PUT("selfi/todolist/{nis}/{id}")
    fun updateTodo(
        @Path("nis") nis: Int,
        @Path("id") id: Int,
        @Field("status") status: String
    ): Call<ResponseDB>

    @DELETE("selfi/todolist/{nis}/{id}")
    fun deleteTodoCompleted(
        @Path("nis") nis: Int,
        @Path("id") id: Int
    ): Call<ResponseDB>

    @FormUrlEncoded
    @PUT("selfi/todolist/{nis}/{id}")
    fun editTodo(
        @Path("nis") nis: Int,
        @Path("id") id: Int,
        @Field("status") status: String,
        @Field("nama_kegiatan")judul: String,
        @Field("tanggal")tanggal: LocalDateTime
    ): Call<ResponseDB>


}

