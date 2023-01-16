package kr.co.befamily.bemily.repository

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.co.befamily.bemily.db.UsersDatabase
import kr.co.befamily.bemily.db.dao.UsersDao
import kr.co.befamily.bemily.db.entity.UsersEntity
import kr.co.befamily.bemily.network.RetrofitInstance
import kr.co.befamily.bemily.network.api.UsersApi
import kr.co.befamily.bemily.network.vo.UsersVo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepository(private val usersApi: UsersApi, private val usersDao: UsersDao) {

    val users = usersDao.getAllUsers()

//    fun reqUsersList(q: String, callback: Callback<UsersVo>) {
//        usersApi.getUsersIntoVo(q).enqueue(object : Callback<UsersVo> {
//            override fun onResponse(call: Call<UsersVo>, response: Response<UsersVo>) {
//                callback.onResponse(call, response)
//                Log.e("reqUsersList", "${response.body()}")
//            }
//
//            override fun onFailure(call: Call<UsersVo>, t: Throwable) {
//                callback.onFailure(call, t)
//            }
//        })
//    }

    fun reqUsersList(q: String, callback: Callback<List<UsersEntity>>) {
        usersApi.getUsersIntoEntity(q).enqueue(object : Callback<List<UsersEntity>> {
            override fun onResponse(call: Call<List<UsersEntity>>, response: Response<List<UsersEntity>>) {
                callback.onResponse(call, response)
                Log.e("Response", "onResponse")
                GlobalScope.launch {
                    usersDao.insertUsers(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<UsersEntity>>, t: Throwable) {
                Log.e("Response", "onFailure")
                callback.onFailure(call, t)
            }
        })
    }
}