package com.example.hiltlibrary.data

import javax.inject.Inject

class UserRepository @Inject constructor() {

    fun getUserName(): String = "Dinkar Bhardwaj"
    fun getUserEmail(): String = "dinkar@example.com"
}
