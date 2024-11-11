package com.angel.coroutineszerotohero.ui.theme

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
    @GET("/users")
    suspend fun getUsers(): Response<MutableList<UserDataResponse>>
}

data class UserDataResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String
)