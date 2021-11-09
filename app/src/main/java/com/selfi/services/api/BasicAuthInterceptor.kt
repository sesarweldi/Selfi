package com.selfi.services.api

import android.content.Context
import com.selfi.services.SharedPrefHelper
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response


class BasicAuthInterceptor(context: Context) : Interceptor{

    val pref = SharedPrefHelper.getInstance(context).getAuthToken()

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val authReq = req.newBuilder()
            .header("Authorization", "bearer ${pref}")
            .build()
        return chain.proceed(authReq)
    }
}