package kr.co.befamily.bemily.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.befamily.bemily.network.vo.Users
import kr.co.befamily.bemily.repository.UsersRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel : ViewModel() {
    private var usersRepository = UsersRepository()

    val usersListLiveData = MutableLiveData<Users>()

    fun reqUsersList(q: String) {
        usersRepository.reqUsersList(q, object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                usersListLiveData.value = response.body()
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {

            }
        })
    }
}