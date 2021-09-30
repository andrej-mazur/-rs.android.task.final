package com.example.task5.api.interceptor

import com.example.task5.BuildConfig
import com.example.task5.constant.Headers
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (originalRequest.header(Headers.API_KEY) != null) {
            return chain.proceed(originalRequest)
        }
        val authenticatedRequest = originalRequest.newBuilder()
            .header(Headers.API_KEY, BuildConfig.CAT_API_KEY)
            .build()
        return chain.proceed(authenticatedRequest)
    }
}
