package com.thuatnguyen.tindersample.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thuatnguyen.tindersample.model.Result
import com.thuatnguyen.tindersample.model.User
import com.thuatnguyen.tindersample.model.data
import com.thuatnguyen.tindersample.model.isSucceeded
import com.thuatnguyen.tindersample.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel @ViewModelInject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _userLiveData = MutableLiveData<Result<List<User>>>()
    val userLiveData: LiveData<Result<List<User>>> = _userLiveData

    fun getUsers(isFavoriteUserMode: Boolean) {
        if (isFavoriteUserMode) {
            getFavoriteUsers()
        } else {
            getUsersFromNetwork()
        }
    }

    fun getUsersFromNetwork() {
        viewModelScope.launch {
            userRepository.getUsersFromNetwork().collect {
                _userLiveData.value = it
            }
        }
    }

    private fun getFavoriteUsers() {
        viewModelScope.launch {
            userRepository.getFavoriteUsersFromLocal().collect {
                _userLiveData.value = it
            }
        }
    }

    fun saveFavoriteUser(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userLiveData.value?.let {
                if (it.isSucceeded) {
                    val userList = it.data!!
                    if (position in userList.indices) {
                        userRepository.saveFavoriteUser(userList[position])
                    }
                }
            }
        }
    }

    fun resetUserData() {
        _userLiveData.value = null
    }
}