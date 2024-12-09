package com.example.lodestar

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NetworkService {
    @Headers("Content-Type: application/json")
    @POST("/upload")
    fun uploadData(@Body data: Map<String, String>): Call<ServerResponse>
}

data class ServerResponse(val photoUrl: String)