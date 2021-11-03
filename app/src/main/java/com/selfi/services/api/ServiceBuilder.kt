package com.selfi.services.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {

    const val BASE_URL = "https://selfi.laam.my.id"
    const val URL = "$BASE_URL/"


    val loggingInterceptor  = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttp = OkHttpClient.Builder()
        //.addInterceptor(BasicAuthInterceptor("selfi", "selfi123"))
        .addInterceptor(loggingInterceptor)
        .build()


    private val builder = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp)

    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

}