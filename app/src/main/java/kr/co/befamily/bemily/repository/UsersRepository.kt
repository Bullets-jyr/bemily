package kr.co.befamily.bemily.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.befamily.bemily.db.dao.UsersDao
import kr.co.befamily.bemily.db.entity.UsersEntity
import kr.co.befamily.bemily.network.api.UsersApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepository(private val usersApi: UsersApi, private val usersDao: UsersDao) {

    val users = usersDao.getAllUsers()

    fun reqUsersList(q: String, callback: Callback<List<UsersEntity>>) {
        usersApi.getUsersIntoEntity(q).enqueue(object : Callback<List<UsersEntity>> {
            override fun onResponse(call: Call<List<UsersEntity>>, response: Response<List<UsersEntity>>) {
                callback.onResponse(call, response)
                CoroutineScope(Dispatchers.IO).launch {
                    usersDao.insertUsers(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<UsersEntity>>, t: Throwable) {
                callback.onFailure(call, t)
            }
        })
    }
}