package com.selfi.services.api


import android.R
import android.content.Context
import com.selfi.services.SharedPrefHelper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {

    const val BASE_URL = "https://selfi.laam.my.id"
    const val URL = "$BASE_URL/"


    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun okHttpClient(context: Context): OkHttpClient{
        return OkHttpClient.Builder()
        //.addInterceptor(loggingInterceptor)
        .addInterceptor(BasicAuthInterceptor(context))
        .build()
    }


    fun <T> buildService(serviceType: Class<T>, context: Context): T {
        val builder = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(context))

        val retrofit = builder.build()

        return retrofit.create(serviceType)
    }

}