package com.example.databindingwithlivedata

// MyViewModel.kt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    // MutableLiveData that holds the user name
    private val _userName = MutableLiveData("John Doe")
    val userName: LiveData<String> get() = _userName

    private val _imageUrl = MutableLiveData("https://cdn.pixabay.com/photo/2023/01/10/00/17/italy-7708551_640.jpg")
    val imageUrl: LiveData<String> get() = _imageUrl

    // Function to update userName
    fun updateUserName(newName: String) {
        _userName.value = newName
    }

    fun updateImageUrl(newUrl: String) {
        _imageUrl.value = newUrl
    }

}
