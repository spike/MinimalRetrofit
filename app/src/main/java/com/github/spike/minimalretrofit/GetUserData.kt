package com.github.spike.minimalretrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory

val baseUrl = "https://api.github.com/"
// if http instead of http, be sure to add
// android:usesCleartextTraffic="true" to application inside manifest

interface GetUserData {
    @GET("users")
    fun getUserInfo(): Call<UserData>
}

object UserDataInstance {
    val getUserData:GetUserData
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        getUserData = retrofit.create(GetUserData::class.java)
    }
}
