package kr.co.befamily.bemily.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.befamily.bemily.repository.UsersRepository

class UsersViewModelFactory(private val usersRepository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            return UsersViewModel(usersRepository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }
}