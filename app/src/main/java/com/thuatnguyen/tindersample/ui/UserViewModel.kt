package com.thuatnguyen.tindersample.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thuatnguyen.tindersample.model.User
import com.thuatnguyen.tindersample.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel @ViewModelInject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _userLiveData = MutableLiveData<List<User>>()
    val userLiveData: LiveData<List<User>> = _userLiveData

    fun getUsers(favoriteUserMode: Boolean) {
        if (favoriteUserMode) {
            getFavoriteUsers()
        } else {
            getNextUsers()
        }
    }

    fun getNextUsers() {
        viewModelScope.launch {
            userRepository.getUsersFromNetwork()
                .collect { _userLiveData.value = it }
        }
    }

    fun saveFavoriteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userLiveData.value?.first()?.let { userRepository.saveFavoriteUser(it) }
        }
    }

    private fun getFavoriteUsers() {
        viewModelScope.launch {
            userRepository.getFavoriteUsers().collect {
                _userLiveData.value = it
            }
        }
    }
}