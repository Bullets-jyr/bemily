package kr.co.befamily.bemily.network.api

import kr.co.befamily.bemily.network.vo.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET("/users")
    fun getUsers(@Query("q") q: String): Call<Users>
}