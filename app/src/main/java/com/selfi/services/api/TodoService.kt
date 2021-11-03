package com.selfi.services.api

import com.selfi.models.Todo
import com.selfi.models.response.DataResponseModel
import com.selfi.models.response.ResponseDB
import retrofit2.Call
import retrofit2.http.*
import java.time.LocalDateTime
import java.time.ZonedDateTime

interface TodoService {

    @GET("selfi/todolist/{nis}")
    fun getTodoByNis(
        @Path("nis") nis: Int
    ) : Call<DataResponseModel<List<Todo>>>

    @FormUrlEncoded
    @POST("selfi/todolist/tambah")
    fun addTodo(
        @Field("id_kegiatan") id_todo: Int,
        @Field("nis") nis: Int,
        @Field("nama_kegiatan") nama_kegiatan: String,
        @Field("tanggal") tanggal_todo: String
      //  @Field("jam") jam_todo: String
    ) : Call<ResponseDB>
}

