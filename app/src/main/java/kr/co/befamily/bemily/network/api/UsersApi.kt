package kr.co.befamily.bemily.network.api

import kr.co.befamily.bemily.db.entity.UsersEntity
import kr.co.befamily.bemily.network.vo.UsersVo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET("/users")
    fun getUsersIntoVo(@Query("q") q: String): Call<UsersVo>

    @GET("/users")
    fun getUsersIntoEntity(@Query("q") q: String): Call<List<UsersEntity>>
}