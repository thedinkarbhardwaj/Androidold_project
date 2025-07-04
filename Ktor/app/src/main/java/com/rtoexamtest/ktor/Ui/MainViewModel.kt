package com.rtoexamtest.ktor.Ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rtoexamtest.ktor.Repository.MainRepository
import com.rtoexamtest.ktor.Util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject constructor(val mainRepository: MainRepository):ViewModel() {

        private val _apiStateFlow:MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
        val apiStateFlow:StateFlow<ApiState> = _apiStateFlow

    fun getPost() = viewModelScope.launch {
        mainRepository.getPost()
            .onStart {
                _apiStateFlow.value = ApiState.Loading
            }.catch {e->
                _apiStateFlow.value = ApiState.Failure(e)

            }.collect{resp->
                _apiStateFlow.value = ApiState.Success(resp)
            }
    }

    }