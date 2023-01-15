package kr.co.befamily.bemily.repository

import kr.co.befamily.bemily.network.RetrofitInstance
import kr.co.befamily.bemily.network.api.UsersApi
import kr.co.befamily.bemily.network.vo.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepository {
    private var usersApi = RetrofitInstance.getRetrofitInstance().create(UsersApi::class.java)

    fun reqUsersList(q: String, callback: Callback<Users>) {
        usersApi.getUsers(q).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                callback.onResponse(call, response)
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                callback.onFailure(call, t)
            }
        })
    }
}