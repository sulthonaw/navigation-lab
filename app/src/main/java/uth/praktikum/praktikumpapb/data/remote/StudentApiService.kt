package uth.praktikum.praktikumpapb.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uth.praktikum.praktikumpapb.data.remote.dto.UserDto
import uth.praktikum.praktikumpapb.data.remote.dto.UserResponse

interface StudentApiService {

    @GET("users")
    fun getUsers(
        @Query("limit") limit: Int = 10
    ): Call<UserResponse>

    @GET("users/{id}")
    fun getUser(
        @Path("id") id: Int
    ): Call<UserDto>

    @GET("users/search")
    fun searchUsers(
        @Query("q") query: String
    ): Call<UserResponse>
}