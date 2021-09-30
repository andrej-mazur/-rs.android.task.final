package com.example.task5.api

import com.example.task5.api.data.Cat
import com.example.task5.api.interceptor.AuthenticationInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("images/search")
    suspend fun search(
        @Query("page") pageNumber: Int,
        @Query("limit") pageSize: Int,
        @Query("order") sortOrder: String
    ): Response<List<Cat>>

    companion object {

        private const val BASE_URL = "https://api.thecatapi.com/v1/"

        fun create(): CatApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(AuthenticationInterceptor())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(CatApi::class.java)
        }
    }
}
