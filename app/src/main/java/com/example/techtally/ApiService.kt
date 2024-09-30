package com.example.techtally

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST("client")
    fun signup(@Body request: SignupRequest): Call<ResponseBody>
}