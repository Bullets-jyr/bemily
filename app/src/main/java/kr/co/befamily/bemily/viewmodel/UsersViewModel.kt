package kr.co.befamily.bemily.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.befamily.bemily.db.entity.UsersEntity
import kr.co.befamily.bemily.repository.UsersRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    val usersLiveData = usersRepository.users

    fun reqUsersList(q: String) {
        usersRepository.reqUsersList(q, object : Callback<List<UsersEntity>> {
            override fun onResponse(call: Call<List<UsersEntity>>, response: Response<List<UsersEntity>>) {
                Log.d("onResponse", "${response.body()}")
            }

            override fun onFailure(call: Call<List<UsersEntity>>, t: Throwable) {
                Log.d("onFailure", "$t")
            }
        })
    }
}