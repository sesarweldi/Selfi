package com.selfi.services.api

import com.selfi.models.Target
import com.selfi.models.TodoCompleted
import com.selfi.models.TodoUncompleted
import com.selfi.models.User
import com.selfi.models.response.DataResponseModel
import com.selfi.models.response.ResponseDB
import com.selfi.models.response.ResponseLogin
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @FormUrlEncoded
    @POST("selfi/login/siswa")
    fun login(
        @Field("nis") nis:String,
        @Field("password") password: String
    ): Call<ResponseLogin>


    @GET("selfi/siswa/{nis}")
    fun getUserById(
        @Path("nis") nis:Int
    ): Call <ResponseLogin>

    @GET("selfi/todolist/{nis}/jumlah")
    fun getTodoUncompleted(
        @Path("nis") nis: Int
    ): Call<DataResponseModel<List<TodoUncompleted>>>


    @GET("selfi/todolist/{nis}/selesai")
    fun getTodoCompleted(
        @Path("nis") nis: Int
    ): Call<DataResponseModel<List<TodoCompleted>>>

    @GET("selfi/target/{nis}/profile")
    fun getTargetProfile(
        @Path("nis") nis: Int
    ): Call<DataResponseModel<List<Target>>>
}