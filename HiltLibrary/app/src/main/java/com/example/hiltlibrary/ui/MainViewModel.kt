package com.example.hiltlibrary.ui

import androidx.lifecycle.ViewModel
import com.example.hiltlibrary.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
//    private val userRepository: UserRepository
) : ViewModel() {

//    fun getName(): String = userRepository.getUserName()
//    fun getEmail(): String = userRepository.getUserEmail()
}