package com.rtoexamtest.ktor.Util

import com.rtoexamtest.ktor.data.Post

sealed class ApiState {

    object Empty:ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class Success(val data:List<Post>) : ApiState()
    object Loading : ApiState()
}