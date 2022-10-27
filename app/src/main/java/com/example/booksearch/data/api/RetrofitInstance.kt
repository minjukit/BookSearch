package com.example.booksearch.api

import com.example.booksearch.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance { //singleton

    // okhttpinterceptor = 서버와 애플리케이션 사이에서 데이터를 인터셉트함
    private val okHttpClient: OkHttpClient by lazy {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create()) //DTO 변환 모시
            .client(okHttpClient) // Logcat에서 패킷내용을 모니터링할 수 있도록
            .baseUrl(BASE_URL)
            .build()
    }

    val api: BookSearchApi by lazy {
        retrofit.create(BookSearchApi::class.java)
    }
}