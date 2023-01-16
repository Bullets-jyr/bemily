package kr.co.befamily.bemily.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.befamily.bemily.db.entity.UsersEntity
import kr.co.befamily.bemily.network.vo.UsersVo
import kr.co.befamily.bemily.repository.UsersRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    val usersLiveData = usersRepository.users
//    val usersListLiveData = MutableLiveData<UsersVo>()
    val usersListLiveData = MutableLiveData<List<UsersEntity>>()

//    fun reqUsersList(q: String) {
//        usersRepository.reqUsersList(q, object : Callback<UsersVo> {
//            override fun onResponse(call: Call<UsersVo>, response: Response<UsersVo>) {
//                usersListLiveData.value = response.body()
//            }
//
//            override fun onFailure(call: Call<UsersVo>, t: Throwable) {
//
//            }
//        })
//    }

    fun reqUsersList(q: String) {
        usersRepository.reqUsersList(q, object : Callback<List<UsersEntity>> {
            override fun onResponse(call: Call<List<UsersEntity>>, response: Response<List<UsersEntity>>) {
                usersListLiveData.value = response.body()
            }

            override fun onFailure(call: Call<List<UsersEntity>>, t: Throwable) {

            }
        })
    }
}