package com.thuatnguyen.tindersample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thuatnguyen.tindersample.model.User
import com.thuatnguyen.tindersample.repository.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userLiveData = MutableLiveData<List<User>>()
    val userLiveData: LiveData<List<User>> = _userLiveData

    fun getNextUsers() {
        viewModelScope.launch {
            userRepository.getUsersFromNetwork()
                .collect { _userLiveData.value = it }
        }
    }
}